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

import org.mybatis.jpetstore.web.actions.AccountActionBean;

@Intercepts(LifecycleStage.EventHandling)
public class TracingInterceptor implements Interceptor {
  private transient final Tracer tracer = Tracing.getTracer();
  private transient final Meter meter = Tracing.getMeter();
  private transient final Attributes optl_attributes = Tracing.getAttributes();
  private transient final LongCounter counter = Tracing.getCounter();

  // 使用ContextKey
  // private static final ContextKey<Span> PARENTSPAN_KEY = ContextKey.named("parentSpan-key");

  // 使用ThreadLocal
  private static ThreadLocal<Span> threadLocal = new ThreadLocal<>();

  public void init() {
  }

  public void destroy() {
  }

  public Resolution intercept(ExecutionContext context) throws Exception {
    Resolution resolution = null;
    ActionBean actionBean = context.getActionBean();

    // 若actionBean不存在或是不帶有TracingAOP註解,直接執行原方法
    if (actionBean == null || (!actionBean.getClass().isAnnotationPresent(TracingAOP.class)
        && context.getHandler().getAnnotation(TracingAOP.class) == null)) {
      System.out.println("Interceptor: null");
      return context.proceed();
      // throw new Exception("actionBean is null in Interceptor");
    } else {
      String actionBeanClassName = actionBean.getClass().getName();
      String actionBeanMethodName = context.getHandler().getName();

      // 執行ActionBean方法前的處理邏輯
      Span span = tracer.spanBuilder(actionBeanClassName + ": " + actionBeanMethodName).startSpan();

      // 使用ThreadLocal
      threadLocal.set(span);

      // 使用ContextKey
      // Context newContext = Context.current().with(PARENTSPAN_KEY, span);
      // newContext.makeCurrent();

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

      try (Scope ss = span.makeCurrent()) {
        // counter++
        counter.add(1, optl_attributes);
        // 執行ActionBean方法
        resolution = context.proceed();
        span.setStatus(StatusCode.OK);
      } catch (Throwable t) {
        span.setStatus(StatusCode.ERROR, t.getMessage());
        span.recordException(t);
      } finally {
        // 執行ActionBean方法後的處理邏輯

        // 若TracingAOP不為空,則將varNames中的變數取出寫入到span中
        TracingAOP tracingAOP = context.getHandler().getAnnotation(TracingAOP.class);
        if (tracingAOP != null) {
          setVarNames(span, tracingAOP, actionBean);
          setComments(span, tracingAOP);
        }
        span.end();

        // 從threadLocal中取出span
        threadLocal.remove();
      }
      return resolution;
    }
  }

  /*
   * 使用ThreadLocal
   */
  public static Span getParentSpan() {
    return threadLocal.get();
  }

  public static void setParentSpan(Span parentSpan) {
    threadLocal.set(parentSpan);
  }

  /*
   * 使用ContextKey
   */
  // public static ContextKey<Span> getParentSpanKey() {
  // return PARENTSPAN_KEY;
  // }

  // public static void setParentSpanKey(Span newParentSpan) {
  // Context newContext = Context.current().with(PARENTSPAN_KEY, newParentSpan);
  // newContext.makeCurrent();
  // }

  public void setVarNames(Span span, TracingAOP tracingAOP, ActionBean actionBean) {

    String[] varNames = tracingAOP.varNames();
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

          // 將TracingAOP annotation指定的變數轉為String並將名稱及值寫入span中
          span.setAttribute(varName, String.valueOf(varValue));

          // 改為寫入span event中

        } catch (Throwable t) {
          span.setStatus(StatusCode.ERROR, t.getMessage());
          span.recordException(t);
        }
      }
    }
  }

  public void setComments(Span span, TracingAOP tracingAOP) {
    String[] comments = tracingAOP.comments();

    if (comments.length != 0) {
      for (int i = 0; i < comments.length; ++i) {
        // 將comments中的任意字串寫入event中
        try {
          span.addEvent(comments[i]);
        } catch (Throwable t) {
          span.setStatus(StatusCode.ERROR, t.getMessage());
          span.recordException(t);
        }
      }
    }
  }
}
