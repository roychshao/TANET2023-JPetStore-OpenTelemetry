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
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TracingAspect {

  private transient final Tracer tracer = Tracing.getTracer();

  // @Around("execution(* org.mybatis.jpetstore..*.*(..)) && !within(org.mybatis.jpetstore.domain.TracingAspect) &&
  // !within(org.mybatis.jpetstore.domain.Tracing)")
  @Around("execution(* (org.mybatis.jpetstore.domain..* || org.mybatis.jpetstore.service..* || org.mybatis.jpetstore.mapper..*).*(..)) && !within(org.mybatis.jpetstore.domain.TracingAspect) && !within(org.mybatis.jpetstore.domain.Tracing)")
  public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {

    Span span = TracingInterceptor.getCurrentSpan();

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
      System.out.println(Context.current());
      result = joinPoint.proceed();
    } finally {
      span.end();
      return result;
    }
  }
}
