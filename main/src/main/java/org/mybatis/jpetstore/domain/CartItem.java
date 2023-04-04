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
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The Class CartItem.
 *
 * @author Eduardo Macarron
 */
public class CartItem implements Serializable {

  private static final long serialVersionUID = 6620528781626504362L;

  private Item item;
  private int quantity;
  private boolean inStock;
  private BigDecimal total;
  private transient final Tracer tracer = Tracing.getTracer();

  public boolean isInStock() {
    return inStock;
  }

  public void setInStock(boolean inStock, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setInStock").setParent(Context.current().with(parentSpan)).startSpan();
    this.inStock = inStock;
    span.end();
  }

  public BigDecimal getTotal() {
    return total;
  }

  public Item getItem(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getItem").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return item;
  }

  public void setItem(Item item, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setItem").setParent(Context.current().with(parentSpan)).startSpan();
    this.item = item;
    calculateTotal(span);
    span.end();
  }

  public int getQuantity(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getQuantity").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return quantity;
  }

  public void setQuantity(int quantity, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setQuantity").setParent(Context.current().with(parentSpan)).startSpan();
    this.quantity = quantity;
    calculateTotal(span);
    span.end();
  }

  public void incrementQuantity(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: incrementQuantity").setParent(Context.current().with(parentSpan))
        .startSpan();
    quantity++;
    calculateTotal(span);
    span.end();
  }

  private void calculateTotal(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: calculateTotal").setParent(Context.current().with(parentSpan)).startSpan();
    total = Optional.ofNullable(item).map(i -> i.getListPrice(span)).map(v -> v.multiply(new BigDecimal(quantity)))
        .orElse(null);
    span.end();
  }

}
