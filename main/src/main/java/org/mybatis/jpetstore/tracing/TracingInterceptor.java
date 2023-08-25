/*
 *    Copyright 2010-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.jpetstore.tracing;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

import java.lang.reflect.Field;
import java.util.*;

import javax.servlet.http.*;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.*;

import org.mybatis.jpetstore.tracing.annotation.*;
import org.mybatis.jpetstore.web.actions.AccountActionBean;

@Intercepts(LifecycleStage.EventHandling)
public class TracingInterceptor implements Interceptor {
  private transient final Tracer tracer = Tracing.getTracer();
  private transient final Meter meter = Tracing.getMeter();
  private transient final Attributes otel_attributes = Tracing.getAttributes();
  private transient final LongCounter counter = Tracing.getCounter();

  public void init() {
  }

  public void destroy() {
  }

  public Resolution intercept(ExecutionContext context) throws Exception {
    Resolution resolution = null;
    ActionBean actionBean = context.getActionBean();

    // 若actionBean不存在或是不帶有EnableTelemetry註解,直接執行原方法
    if (actionBean == null || (!actionBean.getClass().isAnnotationPresent(EnableTelemetry.class)
        && context.getHandler().getAnnotation(EnableTelemetry.class) == null)) {
      System.out.println("Interceptor: null");
      return context.proceed();
    } else {
      String actionBeanClassName = actionBean.getClass().getName();
      String actionBeanMethodName = context.getHandler().getName();

      // 執行ActionBean方法前的處理邏輯
      Span span = tracer.spanBuilder(actionBeanClassName + ": " + actionBeanMethodName).startSpan();

      // 使用ThreadLocal
      ThreadLocalContext.setParentSpan(span);

      // 將sessionId寫入span中
      HttpServletRequest request = context.getActionBeanContext().getRequest();
      HttpSession session = request.getSession();
      span.setAttribute("sessionId", session.getId());

      // 若session中有username則將username寫進span中
      Enumeration<String> attributes = session.getAttributeNames();
      while (attributes.hasMoreElements()) {
        String attribute = (String) attributes.nextElement();
        if ("accountBean".equals(attribute)) {
          AccountActionBean accountBean = (AccountActionBean) session.getAttribute(attribute);
          System.out.println("username: " + accountBean.getUsername());
          span.setAttribute("username", accountBean.getUsername());
        }
      }

      TelemetryConfig telemetryConfig = context.getHandler().getAnnotation(TelemetryConfig.class);

      try (Scope ss = span.makeCurrent()) {
        // 執行ActionBean方法
        resolution = context.proceed();
        if (telemetryConfig != null && telemetryConfig.recordStatus() == true)
          span.setStatus(StatusCode.OK);
      } catch (Throwable t) {
        if (telemetryConfig != null && telemetryConfig.recordStatus() == true)
          span.setStatus(StatusCode.ERROR, t.getMessage());
        if (telemetryConfig != null && telemetryConfig.recordException() == true)
          span.recordException(t);
      } finally {
        // 執行ActionBean方法後處理

        // 若TelemetryConfig不為空,則將varNames中的變數取出寫入到span中
        if (telemetryConfig != null) {
          setVarNames(span, telemetryConfig.varNames(), actionBean);
        }
        span.end();

        ThreadLocalContext.clearParentSpan();
        ThreadLocalContext.clearAttributes();
      }
      return resolution;
    }
  }

  public void setVarNames(Span span, String[] varNames, ActionBean actionBean) {

    if (varNames.length != 0) {
      Object[] varValues = new Object[varNames.length];

      for (int i = 0; i < varNames.length; ++i) {
        String varName = varNames[i];

        // 嘗試獲取varName變數值,如果獲取不到就將Exception寫入span中
        try {
          Field field = actionBean.getClass().getDeclaredField(varName);

          field.setAccessible(true);
          Object varValue = field.get(actionBean);
          varValues[i] = varValue;

          // 將EnableTelemetry annotation指定的變數轉為String並將名稱及值寫入span中
          span.setAttribute(varName, String.valueOf(varValue));
        } catch (Throwable t) {
          span.setStatus(StatusCode.ERROR, t.getMessage());
          span.recordException(t);
        }
      }
    }
  }
}
