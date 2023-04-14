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

/**
 * The Class Item.
 *
 * @author Eduardo Macarron
 */
public class Item implements Serializable {

  private static final long serialVersionUID = -2159121673445254631L;

  private String itemId;
  private String productId;
  private BigDecimal listPrice;
  private BigDecimal unitCost;
  private int supplierId;
  private String status;
  private String attribute1;
  private String attribute2;
  private String attribute3;
  private String attribute4;
  private String attribute5;
  private Product product;
  private int quantity;
  private transient final Tracer tracer = Tracing.getTracer();

  public String getItemId() {
    Span span = tracer.spanBuilder("Domain: getItemId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return itemId;
  }

  public void setItemId(String itemId) {
    Span span = tracer.spanBuilder("Domain: setItemId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.itemId = itemId.trim();
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
    Span span = tracer.spanBuilder("setQuantity").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.quantity = quantity;
    } finally {
      span.end();
    }
  }

  public Product getProduct() {
    Span span = tracer.spanBuilder("Domain: getProduct").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return product;
  }

  public void setProduct(Product product) {
    Span span = tracer.spanBuilder("Domain: setProduct").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.product = product;
    } finally {
      span.end();
    }
  }

  public int getSupplierId() {
    Span span = tracer.spanBuilder("Domain: getSupplierId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return supplierId;
  }

  public void setSupplierId(int supplierId) {
    Span span = tracer.spanBuilder("Domain: setSupplierId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.supplierId = supplierId;
    } finally {
      span.end();
    }
  }

  public BigDecimal getListPrice() {
    Span span = tracer.spanBuilder("Domain: getListPrice").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return listPrice;
  }

  public void setListPrice(BigDecimal listPrice) {
    Span span = tracer.spanBuilder("Domain: setListPrice").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.listPrice = listPrice;
    } finally {
      span.end();
    }
  }

  public BigDecimal getUnitCost() {
    Span span = tracer.spanBuilder("Domain: getUnitCost").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return unitCost;
  }

  public void setUnitCost(BigDecimal unitCost) {
    Span span = tracer.spanBuilder("Domain: setUnitCost").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.unitCost = unitCost;
    } finally {
      span.end();
    }
  }

  public String getStatus() {
    Span span = tracer.spanBuilder("Domain: getStatus").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return status;
  }

  public void setStatus(String status) {
    Span span = tracer.spanBuilder("Domain: setStatus").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.status = status;
    } finally {
      span.end();
    }
  }

  public String getAttribute1() {
    Span span = tracer.spanBuilder("Domain: getAttribute1").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return attribute1;
  }

  public void setAttribute1(String attribute1) {
    Span span = tracer.spanBuilder("Domain: setAttribute1").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.attribute1 = attribute1;
    } finally {
      span.end();
    }
  }

  public String getAttribute2() {
    Span span = tracer.spanBuilder("Domain: getAttribute2").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return attribute2;
  }

  public void setAttribute2(String attribute2) {
    Span span = tracer.spanBuilder("Domain: setAttribute2").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.attribute2 = attribute2;
    } finally {
      span.end();
    }
  }

  public String getAttribute3() {
    Span span = tracer.spanBuilder("Domain: getAttribute3").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return attribute3;
  }

  public void setAttribute3(String attribute3) {
    Span span = tracer.spanBuilder("Domain: setAttribute3").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.attribute3 = attribute3;
    } finally {
      span.end();
    }
  }

  public String getAttribute4() {
    Span span = tracer.spanBuilder("Domain: getAttribute4").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return attribute4;
  }

  public void setAttribute4(String attribute4) {
    Span span = tracer.spanBuilder("Domain: setAttribute4").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.attribute4 = attribute4;
    } finally {
      span.end();
    }
  }

  public String getAttribute5() {
    Span span = tracer.spanBuilder("Domain: getAttribute5").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return attribute5;
  }

  public void setAttribute5(String attribute5) {
    Span span = tracer.spanBuilder("Domain: setAttribute5").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.attribute5 = attribute5;
    } finally {
      span.end();
    }
  }

  @Override
  public String toString() {
    Span span = tracer.spanBuilder("Domain: toString").startSpan();
    String result = "";
    try (Scope ss = span.makeCurrent()) {
      result = "(" + getItemId() + "-" + getProduct().getProductId() + ")";
    } finally {
      span.end();
    }
    return result;
  }

}
