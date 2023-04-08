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

import java.io.Serializable;

/**
 * The Class Product.
 *
 * @author Eduardo Macarron
 */
public class Product implements Serializable {

  private static final long serialVersionUID = -7492639752670189553L;

  private String productId;
  private String categoryId;
  private String name;
  private String description;
  private transient final Tracer tracer = Tracing.getTracer();

  public String getProductId(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getProductId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return productId;
  }

  public void setProductId(String productId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setProductId").setParent(Context.current().with(parentSpan)).startSpan();
    this.productId = productId.trim();
    span.end();
  }

  public String getCategoryId(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getCategoryId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return categoryId;
  }

  public void setCategoryId(String categoryId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setCategoryId").setParent(Context.current().with(parentSpan)).startSpan();
    this.categoryId = categoryId;
    span.end();
  }

  public String getName(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getName").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return name;
  }

  public void setName(String name, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setName").setParent(Context.current().with(parentSpan)).startSpan();
    this.name = name;
    span.end();
  }

  public String getDescription(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getDescription").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return description;
  }

  public void setDescription(String description, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setDescription").setParent(Context.current().with(parentSpan)).startSpan();
    this.description = description;
    span.end();
  }

  @Override
  public String toString() {
    Span span = tracer.spanBuilder("Domain: toString").startSpan();
    return getName(span);
  }

}
