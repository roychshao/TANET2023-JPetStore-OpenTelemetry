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
  private transient Tracer tracer = Tracing.getTracer();

  public boolean isInStock() {
    Span span = tracer.spanBuilder("Domain: isInStock").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return inStock;
  }

  public void setInStock(boolean inStock) {
    Span span = tracer.spanBuilder("Domain: setInStock").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.inStock = inStock;
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

  public void incrementQuantity() {
    Span span = tracer.spanBuilder("Domain: incrementQuantity").startSpan();
    try (Scope ss = span.makeCurrent()) {
      quantity++;
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
