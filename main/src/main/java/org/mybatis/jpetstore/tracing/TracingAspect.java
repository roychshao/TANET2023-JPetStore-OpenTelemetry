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

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;
import io.opentelemetry.context.Scope;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAspect {

  private transient final Tracer tracer = Tracing.getTracer();
  private static final ContextKey<Span> PARENTSPAN_KEY = TracingInterceptor.getParentSpanKey();
  private final Logger logger = LogManager.getLogger(TracingAspect.class);

  // mapper, service由spring管理,可使用Aspectj實做切面
  // actionBean由stripes管理,因此使用stripes的Interceptor來攔截
  // @Around("execution(* (org.mybatis.jpetstore.domain..* || org.mybatis.jpetstore.service..* ||
  // org.mybatis.jpetstore.mapper..*).*(..))")
  @Around("@annotation(org.mybatis.jpetstore.tracing.TracingAOP) || @within(org.mybatis.jpetstore.tracing.TracingAOP)")
  public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {

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
      logger.info("log into spans");
      result = joinPoint.proceed();
      span.setStatus(StatusCode.OK);
    } catch (Throwable t) {
      span.setStatus(StatusCode.ERROR, t.getMessage());
      span.recordException(t);
    } finally {
      // TODO: signature.getMethod的注意事項:
      // 1.只能獲取public方法
      // 2.需要另外獲得方法參數
      // 3.遇到override時會回傳匹配到該方法的第一個方法
      // 因此盡可能改使用其他方法getMethod
      // ex:
      // String methodName = joinPoint.getSignature().getName();
      // Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
      // Method method = declaringType.getMethod(methodName, String.class);
      // 缺點: getMethod需要明確定義參數的類型
      // => 找到解決方法
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      // Method method = signature.getMethod();
      Class<?> targetClass = joinPoint.getTarget().getClass();
      Method method = targetClass.getDeclaredMethod(signature.getName(), signature.getParameterTypes());

      TracingAOP tracingAOP = method.getAnnotation(TracingAOP.class);
      // 若tracingVar不為空,則將varNames中的變數取出寫入到span中
      if (tracingAOP != null) {
        setVarNames(span, tracingAOP, joinPoint);
        setComments(span, tracingAOP);
      }
      span.end();
    }
    return result;
  }

  public void setVarNames(Span span, TracingAOP tracingAOP, ProceedingJoinPoint joinPoint) {

    String[] varNames = tracingAOP.varNames();

    if (varNames.length != 0) {
      Object[] varValues = new Object[varNames.length];

      for (int i = 0; i < varNames.length; ++i) {
        String varName = varNames[i];

        try {
          // 嘗試獲取varName變數值,如果獲取不到就將Exception寫入span中
          Field field = joinPoint.getTarget().getClass().getDeclaredField(varName);
          field.setAccessible(true);
          Object varValue = field.get(joinPoint.getTarget());
          varValues[i] = varValue;
          // 將TracingVar annotation指定的變數轉為String並將名稱及值寫入span中
          span.setAttribute(varName, String.valueOf(varValue));
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
