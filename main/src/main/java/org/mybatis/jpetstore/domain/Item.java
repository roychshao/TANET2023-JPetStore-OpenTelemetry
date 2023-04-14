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

  public String getItemId(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getItemId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return itemId;
  }

  public void setItemId(String itemId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setItemId").setParent(Context.current().with(parentSpan)).startSpan();
    this.itemId = itemId.trim();
    span.end();
  }

  public int getQuantity(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getQuantity").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return quantity;
  }

  public void setQuantity(int quantity, Span parentSpan) {
    Span span = tracer.spanBuilder("setQuantity").setParent(Context.current().with(parentSpan)).startSpan();
    this.quantity = quantity;
    span.end();
  }

  public Product getProduct(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getProduct").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return product;
  }

  public void setProduct(Product product, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setProduct").setParent(Context.current().with(parentSpan)).startSpan();
    this.product = product;
    span.end();
  }

  public int getSupplierId(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getSupplierId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return supplierId;
  }

  public void setSupplierId(int supplierId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setSupplierId").setParent(Context.current().with(parentSpan)).startSpan();
    this.supplierId = supplierId;
    span.end();
  }

  public BigDecimal getListPrice(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getListPrice").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return listPrice;
  }

  public void setListPrice(BigDecimal listPrice, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setListPrice").setParent(Context.current().with(parentSpan)).startSpan();
    this.listPrice = listPrice;
    span.end();
  }

  public BigDecimal getUnitCost(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getUnitCost").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return unitCost;
  }

  public void setUnitCost(BigDecimal unitCost, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setUnitCost").setParent(Context.current().with(parentSpan)).startSpan();
    this.unitCost = unitCost;
    span.end();
  }

  public String getStatus(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getStatus").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return status;
  }

  public void setStatus(String status, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setStatus").setParent(Context.current().with(parentSpan)).startSpan();
    this.status = status;
    span.end();
  }

  public String getAttribute1(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getAttribute1").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return attribute1;
  }

  public void setAttribute1(String attribute1, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setAttribute1").setParent(Context.current().with(parentSpan)).startSpan();
    this.attribute1 = attribute1;
    span.end();
  }

  public String getAttribute2(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getAttribute2").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return attribute2;
  }

  public void setAttribute2(String attribute2, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setAttribute2").setParent(Context.current().with(parentSpan)).startSpan();
    this.attribute2 = attribute2;
    span.end();
  }

  public String getAttribute3(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getAttribute3").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return attribute3;
  }

  public void setAttribute3(String attribute3, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setAttribute3").setParent(Context.current().with(parentSpan)).startSpan();
    this.attribute3 = attribute3;
    span.end();
  }

  public String getAttribute4(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getAttribute4").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return attribute4;
  }

  public void setAttribute4(String attribute4, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setAttribute4").setParent(Context.current().with(parentSpan)).startSpan();
    this.attribute4 = attribute4;
    span.end();
  }

  public String getAttribute5(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getAttribute5").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return attribute5;
  }

  public void setAttribute5(String attribute5, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setAttribute5").setParent(Context.current().with(parentSpan)).startSpan();
    this.attribute5 = attribute5;
    span.end();
  }

  @Override
  public String toString() {
    Span span = tracer.spanBuilder("Domain: toString").startSpan();
    String result = "";
    result = "(" + getItemId(span) + "-" + getProduct(span).getProductId(span) + ")";
    span.end();
    return result;
  }

}
