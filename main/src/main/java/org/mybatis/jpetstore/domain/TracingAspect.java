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

import javax.servlet.http.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAspect {

  private transient final Tracer tracer = Tracing.getTracer();
  private static final ContextKey<Span> PARENTSPAN_KEY = TracingInterceptor.getParentSpanKey();

  // mapper, service由spring管理,可使用Aspectj實做切面
  // actionBean由stripes管理,因此使用stripes的Interceptor來攔截
  @Around("execution(* (org.mybatis.jpetstore.domain..* || org.mybatis.jpetstore.service..* || org.mybatis.jpetstore.mapper..*).*(..))")
  public Object trace(ProceedingJoinPoint joinPoint/* , TracingVar tracingVar */) throws Throwable {

    // 從Context中取得parentSpan
    Span span = Context.current().get(PARENTSPAN_KEY);

    if (span == null) {
      span = tracer
          .spanBuilder(joinPoint.getSignature().getDeclaringTypeName() + ": " + joinPoint.getSignature().getName())
          .startSpan();
    } else {
      span = tracer
          .spanBuilder(joinPoint.getSignature().getDeclaringTypeName() + ": " + joinPoint.getSignature().getName())
          .setParent(Context.current().with(span)).startSpan();
    }

    Object result = null;
    try (Scope ss = span.makeCurrent()) {
      result = joinPoint.proceed();
      span.setStatus(StatusCode.OK);
    } catch (Throwable t) {
      span.setStatus(StatusCode.ERROR, t.getMessage());
      span.recordException(t);
    } finally {
      // // 若tracingVar不為空,則將varNames中的變數取出寫入到span中
      // if (tracingVar != null) {
      // String[] varNames = tracingVar.varNames();
      // if (varNames.length != 0) {
      // Object[] varValues = new Object[varNames.length];

      // for (int i = 0; i < varNames.length; ++i) {
      // String varName = varNames[i];

      // try {
      // // 嘗試獲取varName變數值,如果獲取不到就將Exception寫入span中
      // Field field = joinPoint.getTarget().getClass().getDeclaredField(varName);
      // field.setAccessible(true);
      // Object varValue = field.get(joinPoint.getTarget());
      // varValues[i] = varValue;
      // System.out.println("aspect after method, " + varName + ": " + varValue);
      // } catch(Throwable t) {
      // span.setStatus(StatusCode.ERROR, t.getMessage());
      // span.recordException(t);
      // }
      // }
      // }
      // } else {
      // System.out.println("tracingVar is: " + tracingVar);
      // }
      span.end();
    }
    return result;
  }
}
