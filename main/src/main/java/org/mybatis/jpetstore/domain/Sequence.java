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
import io.opentelemetry.context.Scope;

import java.io.Serializable;

/**
 * The Class Sequence.
 *
 * @author Eduardo Macarron
 */
public class Sequence implements Serializable {

  private static final long serialVersionUID = 8278780133180137281L;

  private String name;
  private int nextId;
  private transient Tracer tracer = Tracing.getTracer();

  public Sequence() {
  }

  public Sequence(String name, int nextId) {
    this.name = name;
    this.nextId = nextId;
  }

  public String getName() {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Sequence Domain: getName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return name;
  }

  public void setName(String name) {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Sequence Domain: setName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.name = name;
    } finally {
      span.end();
    }
  }

  public int getNextId() {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Sequence Domain: getNextId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return nextId;
  }

  public void setNextId(int nextId) {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Sequence Domain: setNextId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.nextId = nextId;
    } finally {
      span.end();
    }
  }

}
