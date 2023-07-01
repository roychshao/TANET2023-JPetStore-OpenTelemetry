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
 * The Class Category.
 *
 * @author Eduardo Macarron
 */
public class Category implements Serializable {

  private static final long serialVersionUID = 3992469837058393712L;

  private String categoryId;
  private String name;
  private String description;
  private transient Tracer tracer = Tracing.opentelemetry.getTracer("jpetstore-autoconfig", "1.0.0");

  public String getCategoryId() {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: getCategoryId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: setCategoryId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.categoryId = categoryId.trim();
    } finally {
      span.end();
    }
  }

  public String getName() {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: getName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return name;
  }

  public void setName(String name) {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: setName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.name = name;
    } finally {
      span.end();
    }
  }

  public String getDescription() {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: getDescription").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return description;
  }

  public void setDescription(String description) {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: setDescription").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.description = description;
    } finally {
      span.end();
    }
  }

  @Override
  public String toString() {
    tracer = Tracing.getTracer();
    Span span = tracer.spanBuilder("Domain: toString").startSpan();
    String result = "";
    try (Scope ss = span.makeCurrent()) {
      result = getCategoryId();
    } finally {
      span.end();
    }
    return result;
  }

}
