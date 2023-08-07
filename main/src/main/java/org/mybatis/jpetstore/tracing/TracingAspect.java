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
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
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
import org.mybatis.jpetstore.tracing.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAspect {

  private transient final Tracer tracer = Tracing.getTracer();
  // private static final ContextKey<Span> PARENTSPAN_KEY = TracingInterceptor.getParentSpanKey();
  private transient final Logger logger = LogManager.getLogger(TracingAspect.class);
  private transient final Meter meter = Tracing.getMeter();
  private transient final LongCounter counter = Tracing.getCounter();
  private transient final Attributes otel_attributes = Tracing.getAttributes();

  @Around("@annotation(org.mybatis.jpetstore.tracing.annotation.TracingAOP) || @within(org.mybatis.jpetstore.tracing.annotation.TracingAOP)")
  public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {

    // 獲得joinPoint資訊
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Class<?> targetClass = joinPoint.getTarget().getClass();
    Method method = targetClass.getDeclaredMethod(signature.getName(), signature.getParameterTypes());

    // 使用ThreadLocal
    Span parentSpan = TracingInterceptor.getParentSpan();
    Span span = null;

    // 獲得SpanConfig註解
    SpanConfig spanConfig = method.getAnnotation(SpanConfig.class);
    SpanKind kindValue = SpanKind.INTERNAL; // SpanKind預設為Internal
    boolean recordStatus = false;
    boolean recordException = false;
    if (spanConfig != null) {
      // 決定span kind(預設INTERNAL)
      String spanKind = spanConfig.kind();
      if (spanKind != "") {
        switch (spanKind) {
          case "Client":
            kindValue = SpanKind.CLIENT;
            break;
          case "Server":
            kindValue = SpanKind.SERVER;
            break;
          case "Interval":
            kindValue = SpanKind.INTERNAL;
            break;
          case "Consumer":
            kindValue = SpanKind.CONSUMER;
            break;
          case "Producer":
            kindValue = SpanKind.PRODUCER;
            break;
          default:
            kindValue = SpanKind.INTERNAL;
        }
      }
      // 決定是否紀錄status和exception
      recordStatus = spanConfig.recordStatus();
      recordException = spanConfig.recordException();
    }

    if (parentSpan == null) {
      span = tracer
          .spanBuilder(joinPoint.getSignature().getDeclaringTypeName() + ": " + joinPoint.getSignature().getName())
          .setSpanKind(kindValue).startSpan();
    } else {
      span = tracer
          .spanBuilder(joinPoint.getSignature().getDeclaringTypeName() + ": " + joinPoint.getSignature().getName())
          .setSpanKind(kindValue).setParent(Context.current().with(parentSpan)).startSpan();
      // 將原parentSpan改為現方法的span
      TracingInterceptor.setParentSpan(span);
    }


    Object result = null;
    try (Scope ss = span.makeCurrent()) {
      // logger.info("log into spans");
      result = joinPoint.proceed();
      if (recordStatus)
        span.setStatus(StatusCode.OK);
    } catch (Throwable t) {
      if (recordStatus)
        span.setStatus(StatusCode.ERROR, t.getMessage());
      if (recordException)
        span.recordException(t);
    } finally {
      // annotaion添加相關功能
      TracingAOP tracingAOP = method.getAnnotation(TracingAOP.class);
      if (tracingAOP != null) {
        setVarNames(span, tracingAOP, joinPoint);
      }
      if (spanConfig != null) {
        // for attributes
        SpanConfig.KeyValue[] attributes = spanConfig.attributes();
        if (attributes.length != 0) {
          for (SpanConfig.KeyValue keyValue : attributes) {
            span.setAttribute(keyValue.key(), keyValue.value());
          }
        }
      }
      CounterConfig counterConfig = method.getAnnotation(CounterConfig.class);
      if (counterConfig != null) {
        counter.add(counterConfig.incrementBy(), otel_attributes);
      }

      TracingInterceptor.setParentSpan(parentSpan);
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