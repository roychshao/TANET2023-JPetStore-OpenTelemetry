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
      this.quantity = cartItem.getQuantity();
      this.itemId = cartItem.getItem().getItemId();
      this.unitPrice = cartItem.getItem().getListPrice();
      this.item = cartItem.getItem();
      calculateTotal();
    } finally {
      span.end();
    }
  }

  public int getOrderId() {
    Span span = tracer.spanBuilder("Domain: getOrderId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return orderId;
  }

  public void setOrderId(int orderId) {
    Span span = tracer.spanBuilder("Domain: setOrderId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.orderId = orderId;
    } finally {
      span.end();
    }
  }

  public int getLineNumber() {
    Span span = tracer.spanBuilder("Domain: getLineNumber").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return lineNumber;
  }

  public void setLineNumber(int lineNumber) {
    Span span = tracer.spanBuilder("Domain: setLineNumber").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.lineNumber = lineNumber;
    } finally {
      span.end();
    }
  }

  public String getItemId() {
    Span span = tracer.spanBuilder("Domain: getItemId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return itemId;
  }

  public void setItemId(String itemId) {
    Span span = tracer.spanBuilder("Domain: getItemId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.itemId = itemId;
    } finally {
      span.end();
    }
  }

  public BigDecimal getUnitPrice() {
    Span span = tracer.spanBuilder("Domain: getUnitPrice").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitprice) {
    Span span = tracer.spanBuilder("Domain: setUnitPrice").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.unitPrice = unitprice;
    } finally {
      span.end();
    }
  }

  public BigDecimal getTotal() {
    Span span = tracer.spanBuilder("Domain: getTotal").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return total;
  }

  public Item getItem() {
    Span span = tracer.spanBuilder("Domain: getItem").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return item;
  }

  public void setItem(Item item) {
    Span span = tracer.spanBuilder("Domain: setItem").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.item = item;
      calculateTotal();
    } finally {
      span.end();
    }
  }

  public int getQuantity() {
    Span span = tracer.spanBuilder("Domain: getQuantity").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return quantity;
  }

  public void setQuantity(int quantity) {
    Span span = tracer.spanBuilder("Domain: setQuantity").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.quantity = quantity;
      calculateTotal();
    } finally {
      span.end();
    }
  }

  private void calculateTotal() {
    Span span = tracer.spanBuilder("Domain: calculateTotal").startSpan();
    try (Scope ss = span.makeCurrent()) {
      total = Optional.ofNullable(item).map(i -> i.getListPrice()).map(v -> v.multiply(new BigDecimal(quantity)))
          .orElse(null);
    } finally {
      span.end();
    }
  }

}
