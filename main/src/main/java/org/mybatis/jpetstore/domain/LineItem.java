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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * The Class LineItem.
 *
 * @author Eduardo Macarron
 */
public class LineItem implements Serializable {

  private static final long serialVersionUID = 6804536240033522156L;

  private int orderId;
  private int lineNumber;
  private int quantity;
  private String itemId;
  private BigDecimal unitPrice;
  private Item item;
  private BigDecimal total;
  private transient final Tracer tracer = Tracing.getTracer();

  public LineItem() {
  }

  /**
   * Instantiates a new line item.
   *
   * @param lineNumber
   *          the line number
   * @param cartItem
   *          the cart item
   */
  public LineItem(int lineNumber, CartItem cartItem) {
    Span span = tracer.spanBuilder("Domain: LineItem Contructor").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.lineNumber = lineNumber;
      this.quantity = cartItem.getQuantity(span);
      this.itemId = cartItem.getItem(span).getItemId(span);
      this.unitPrice = cartItem.getItem(span).getListPrice(span);
      this.item = cartItem.getItem(span);
      calculateTotal(span);
    } finally {
      span.end();
    }
  }

  public int getOrderId() {
    return orderId;
  }

  public void setOrderId(int orderId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setOrderId").setParent(Context.current().with(parentSpan)).startSpan();
    this.orderId = orderId;
    span.end();
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public String getItemId(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getItemId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return itemId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitprice) {
    this.unitPrice = unitprice;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public Item getItem() {
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

  private void calculateTotal(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: calculateTotal").setParent(Context.current().with(parentSpan)).startSpan();
    total = Optional.ofNullable(item).map(i -> i.getListPrice(span)).map(v -> v.multiply(new BigDecimal(quantity)))
        .orElse(null);
    span.end();
  }

}
