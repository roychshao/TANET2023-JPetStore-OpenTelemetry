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
package org.mybatis.jpetstore.domain;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.Scope;

import java.util.*;

import javax.servlet.http.*;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.*;

import org.mybatis.jpetstore.web.actions.AccountActionBean;

@Intercepts(LifecycleStage.EventHandling)
public class TracingInterceptor implements Interceptor {
  private transient final Tracer tracer = Tracing.getTracer();
  private static final ContextKey<Span> PARENTSPAN_KEY = ContextKey.named("parentSpan-key");

  public void init() {
  }

  public void destroy() {
  }

  public Resolution intercept(ExecutionContext context) throws Exception {
    Resolution resolution = null;
    ActionBean actionBean = context.getActionBean();
    if (actionBean == null) {
      System.out.println("Interceptor: actionBean is null");
      return null;
    } else {
      String actionBeanClassName = actionBean.getClass().getName();
      String actionBeanMethodName = context.getHandler().getName();

      // 執行ActionBean方法前的處理邏輯
      Span span = tracer.spanBuilder(actionBeanClassName + ": " + actionBeanMethodName).startSpan();
      Context newContext = Context.current().with(PARENTSPAN_KEY, span);
      newContext.makeCurrent();

      // 將sessionId寫入span中
      HttpServletRequest request = context.getActionBeanContext().getRequest();
      HttpSession session = request.getSession();
      span.setAttribute("sessionId", session.getId());

      // 若session中有username則將username寫進span中
      Enumeration<String> attributes = session.getAttributeNames();
      while (attributes.hasMoreElements()) {
        String attribute = (String) attributes.nextElement();
        System.out.println(attribute + " : " + session.getAttribute(attribute));
        if (attribute == "accountBean") {
          AccountActionBean accountBean = (AccountActionBean) session.getAttribute(attribute);
          System.out.println("username: " + accountBean.getUsername());
          span.setAttribute("username", accountBean.getUsername());
        }
      }

      try (Scope ss = span.makeCurrent()) {
        // 執行ActionBean方法
        resolution = context.proceed();
        span.setStatus(StatusCode.OK);
      } catch (Throwable t) {
        span.setStatus(StatusCode.ERROR, t.getMessage());
        span.recordException(t);
      } finally {
        // 執行ActionBean方法後的處理邏輯
        span.end();
      }
      return resolution;
    }
  }

  public static ContextKey getParentSpanKey() {
    return PARENTSPAN_KEY;
  }
}