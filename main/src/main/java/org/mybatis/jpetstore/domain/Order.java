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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The Class Order.
 *
 * @author Eduardo Macarron
 */
public class Order implements Serializable {

  private static final long serialVersionUID = 6321792448424424931L;

  private int orderId;
  private String username;
  private Date orderDate;
  private String shipAddress1;
  private String shipAddress2;
  private String shipCity;
  private String shipState;
  private String shipZip;
  private String shipCountry;
  private String billAddress1;
  private String billAddress2;
  private String billCity;
  private String billState;
  private String billZip;
  private String billCountry;
  private String courier;
  private BigDecimal totalPrice;
  private String billToFirstName;
  private String billToLastName;
  private String shipToFirstName;
  private String shipToLastName;
  private String creditCard;
  private String expiryDate;
  private String cardType;
  private String locale;
  private String status;
  private List<LineItem> lineItems = new ArrayList<>();
  private transient final Tracer tracer = Tracing.getTracer();

  public int getOrderId(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getOrderId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return orderId;
  }

  public void setOrderId(int orderId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setOrderId").setParent(Context.current().with(parentSpan)).startSpan();
    this.orderId = orderId;
    span.end();
  }

  public String getUsername(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getUsername").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return username;
  }

  public void setUsername(String username, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setUsername").setParent(Context.current().with(parentSpan)).startSpan();
    this.username = username;
    span.end();
  }

  public Date getOrderDate(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getOrderDate").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return orderDate;
  }

  public void setOrderDate(Date orderDate, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setOrderDate").setParent(Context.current().with(parentSpan)).startSpan();
    this.orderDate = orderDate;
    span.end();
  }

  public String getShipAddress1(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipAddress1").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return shipAddress1;
  }

  public void setShipAddress1(String shipAddress1, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipAddress1").setParent(Context.current().with(parentSpan)).startSpan();
    this.shipAddress1 = shipAddress1;
    span.end();
  }

  public String getShipAddress2(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipAddress2").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return shipAddress2;
  }

  public void setShipAddress2(String shipAddress2, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipAddress2").setParent(Context.current().with(parentSpan)).startSpan();
    this.shipAddress2 = shipAddress2;
    span.end();
  }

  public String getShipCity(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipCity").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return shipCity;
  }

  public void setShipCity(String shipCity, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipCity").setParent(Context.current().with(parentSpan)).startSpan();
    this.shipCity = shipCity;
    span.end();
  }

  public String getShipState(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipState").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return shipState;
  }

  public void setShipState(String shipState, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipState").setParent(Context.current().with(parentSpan)).startSpan();
    this.shipState = shipState;
    span.end();
  }

  public String getShipZip(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipZip").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return shipZip;
  }

  public void setShipZip(String shipZip, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipZip").setParent(Context.current().with(parentSpan)).startSpan();
    this.shipZip = shipZip;
    span.end();
  }

  public String getShipCountry(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipCountry").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return shipCountry;
  }

  public void setShipCountry(String shipCountry, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipCountry").setParent(Context.current().with(parentSpan)).startSpan();
    this.shipCountry = shipCountry;
    span.end();
  }

  public String getBillAddress1(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillAddress1").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return billAddress1;
  }

  public void setBillAddress1(String billAddress1, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillAddress1").setParent(Context.current().with(parentSpan)).startSpan();
    this.billAddress1 = billAddress1;
    span.end();
  }

  public String getBillAddress2(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillAddress2").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return billAddress2;
  }

  public void setBillAddress2(String billAddress2, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillAddress2").setParent(Context.current().with(parentSpan)).startSpan();
    this.billAddress2 = billAddress2;
    span.end();
  }

  public String getBillCity(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillCity").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return billCity;
  }

  public void setBillCity(String billCity, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillCity").setParent(Context.current().with(parentSpan)).startSpan();
    this.billCity = billCity;
    span.end();
  }

  public String getBillState(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillState").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return billState;
  }

  public void setBillState(String billState, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillState").setParent(Context.current().with(parentSpan)).startSpan();
    this.billState = billState;
    span.end();
  }

  public String getBillZip(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillZip").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return billZip;
  }

  public void setBillZip(String billZip, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillZip").setParent(Context.current().with(parentSpan)).startSpan();
    this.billZip = billZip;
    span.end();
  }

  public String getBillCountry(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillCountry").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return billCountry;
  }

  public void setBillCountry(String billCountry, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillCountry").setParent(Context.current().with(parentSpan)).startSpan();
    this.billCountry = billCountry;
    span.end();
  }

  public String getCourier(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getCourier").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return courier;
  }

  public void setCourier(String courier, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setCourier").setParent(Context.current().with(parentSpan)).startSpan();
    this.courier = courier;
    span.end();
  }

  public BigDecimal getTotalPrice(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getTotalPrice").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setTotalPrice").setParent(Context.current().with(parentSpan)).startSpan();
    this.totalPrice = totalPrice;
    span.end();
  }

  public String getBillToFirstName(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillToFirstName").setParent(Context.current().with(parentSpan))
        .startSpan();
    span.end();
    return billToFirstName;
  }

  public void setBillToFirstName(String billToFirstName, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillToFirstName").setParent(Context.current().with(parentSpan))
        .startSpan();
    this.billToFirstName = billToFirstName;
    span.end();
  }

  public String getBillToLastName(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getBillToLastName").setParent(Context.current().with(parentSpan))
        .startSpan();
    span.end();
    return billToLastName;
  }

  public void setBillToLastName(String billToLastName, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setBillToLastName").setParent(Context.current().with(parentSpan))
        .startSpan();
    this.billToLastName = billToLastName;
    span.end();
  }

  public String getShipToFirstName(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipToFirstName").setParent(Context.current().with(parentSpan))
        .startSpan();
    span.end();
    return shipToFirstName;
  }

  public void setShipToFirstName(String shipFoFirstName, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipToFirstName").setParent(Context.current().with(parentSpan))
        .startSpan();
    this.shipToFirstName = shipFoFirstName;
    span.end();
  }

  public String getShipToLastName(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getShipToLastName").setParent(Context.current().with(parentSpan))
        .startSpan();
    span.end();
    return shipToLastName;
  }

  public void setShipToLastName(String shipToLastName, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setShipToLastName").setParent(Context.current().with(parentSpan))
        .startSpan();
    this.shipToLastName = shipToLastName;
    span.end();
  }

  public String getCreditCard(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getCreditCard").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return creditCard;
  }

  public void setCreditCard(String creditCard, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setCreditCard").setParent(Context.current().with(parentSpan)).startSpan();
    this.creditCard = creditCard;
    span.end();
  }

  public String getExpiryDate(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getExpiryDate").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setExpiryDate").setParent(Context.current().with(parentSpan)).startSpan();
    this.expiryDate = expiryDate;
    span.end();
  }

  public String getCardType(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getCardType").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return cardType;
  }

  public void setCardType(String cardType, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setCardType").setParent(Context.current().with(parentSpan)).startSpan();
    this.cardType = cardType;
    span.end();
  }

  public String getLocale(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getLocale").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return locale;
  }

  public void setLocale(String locale, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setLocale").setParent(Context.current().with(parentSpan)).startSpan();
    this.locale = locale;
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

  public void setLineItems(List<LineItem> lineItems, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setLineItems").setParent(Context.current().with(parentSpan)).startSpan();
    this.lineItems = lineItems;
    span.end();
  }

  public List<LineItem> getLineItems(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getLineItems").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return lineItems;
  }

  /**
   * Inits the order.
   *
   * @param account
   *          the account
   * @param cart
   *          the cart
   */
  public void initOrder(Account account, Cart cart, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: initOrder").setParent(Context.current().with(parentSpan)).startSpan();

    username = account.getUsername(span);
    orderDate = new Date();

    shipToFirstName = account.getFirstName(span);
    shipToLastName = account.getLastName(span);
    shipAddress1 = account.getAddress1(span);
    shipAddress2 = account.getAddress2(span);
    shipCity = account.getCity(span);
    shipState = account.getState(span);
    shipZip = account.getZip(span);
    shipCountry = account.getCountry(span);

    billToFirstName = account.getFirstName(span);
    billToLastName = account.getLastName(span);
    billAddress1 = account.getAddress1(span);
    billAddress2 = account.getAddress2(span);
    billCity = account.getCity(span);
    billState = account.getState(span);
    billZip = account.getZip(span);
    billCountry = account.getCountry(span);

    totalPrice = cart.getSubTotal(span);

    creditCard = "999 9999 9999 9999";
    expiryDate = "12/03";
    cardType = "Visa";
    courier = "UPS";
    locale = "CA";
    status = "P";

    Iterator<CartItem> i = cart.getAllCartItems(span);
    while (i.hasNext()) {
      CartItem cartItem = i.next();
      addLineItem(cartItem, span);
    }

  }

  public void addLineItem(CartItem cartItem, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: addLineItem (CartItem)").setParent(Context.current().with(parentSpan))
        .startSpan();
    LineItem lineItem = new LineItem(lineItems.size() + 1, cartItem);
    addLineItem(lineItem, span);
    span.end();
  }

  public void addLineItem(LineItem lineItem, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: addLineItem (LineItem)").setParent(Context.current().with(parentSpan))
        .startSpan();
    lineItems.add(lineItem);
    span.end();
  }

}
