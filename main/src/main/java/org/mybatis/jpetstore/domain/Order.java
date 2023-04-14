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

  public String getUsername() {
    Span span = tracer.spanBuilder("Domain: getUsername").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return username;
  }

  public void setUsername(String username) {
    Span span = tracer.spanBuilder("Domain: setUsername").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.username = username;
    } finally {
      span.end();
    }
  }

  public Date getOrderDate() {
    Span span = tracer.spanBuilder("Domain: getOrderDate").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return orderDate;
  }

  public void setOrderDate(Date orderDate) {
    Span span = tracer.spanBuilder("Domain: setOrderDate").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.orderDate = orderDate;
    } finally {
      span.end();
    }
  }

  public String getShipAddress1() {
    Span span = tracer.spanBuilder("Domain: getShipAddress1").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipAddress1;
  }

  public void setShipAddress1(String shipAddress1) {
    Span span = tracer.spanBuilder("Domain: setShipAddress1").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipAddress1 = shipAddress1;
    } finally {
      span.end();
    }
  }

  public String getShipAddress2() {
    Span span = tracer.spanBuilder("Domain: getShipAddress2").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipAddress2;
  }

  public void setShipAddress2(String shipAddress2) {
    Span span = tracer.spanBuilder("Domain: setShipAddress2").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipAddress2 = shipAddress2;
    } finally {
      span.end();
    }
  }

  public String getShipCity() {
    Span span = tracer.spanBuilder("Domain: getShipCity").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipCity;
  }

  public void setShipCity(String shipCity) {
    Span span = tracer.spanBuilder("Domain: setShipCity").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipCity = shipCity;
    } finally {
      span.end();
    }
  }

  public String getShipState() {
    Span span = tracer.spanBuilder("Domain: getShipState").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipState;
  }

  public void setShipState(String shipState) {
    Span span = tracer.spanBuilder("Domain: setShipState").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipState = shipState;
    } finally {
      span.end();
    }
  }

  public String getShipZip() {
    Span span = tracer.spanBuilder("Domain: getShipZip").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipZip;
  }

  public void setShipZip(String shipZip) {
    Span span = tracer.spanBuilder("Domain: setShipZip").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipZip = shipZip;
    } finally {
      span.end();
    }
  }

  public String getShipCountry() {
    Span span = tracer.spanBuilder("Domain: getShipCountry").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipCountry;
  }

  public void setShipCountry(String shipCountry) {
    Span span = tracer.spanBuilder("Domain: setShipCountry").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipCountry = shipCountry;
    } finally {
      span.end();
    }
  }

  public String getBillAddress1() {
    Span span = tracer.spanBuilder("Domain: getBillAddress1").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billAddress1;
  }

  public void setBillAddress1(String billAddress1) {
    Span span = tracer.spanBuilder("Domain: setBillAddress1").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billAddress1 = billAddress1;
    } finally {
      span.end();
    }
  }

  public String getBillAddress2() {
    Span span = tracer.spanBuilder("Domain: getBillAddress2").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billAddress2;
  }

  public void setBillAddress2(String billAddress2) {
    Span span = tracer.spanBuilder("Domain: setBillAddress2").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billAddress2 = billAddress2;
    } finally {
      span.end();
    }
  }

  public String getBillCity() {
    Span span = tracer.spanBuilder("Domain: getBillCity").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billCity;
  }

  public void setBillCity(String billCity) {
    Span span = tracer.spanBuilder("Domain: setBillCity").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billCity = billCity;
    } finally {
      span.end();
    }
  }

  public String getBillState() {
    Span span = tracer.spanBuilder("Domain: getBillState").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billState;
  }

  public void setBillState(String billState) {
    Span span = tracer.spanBuilder("Domain: setBillState").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billState = billState;
    } finally {
      span.end();
    }
  }

  public String getBillZip() {
    Span span = tracer.spanBuilder("Domain: getBillZip").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billZip;
  }

  public void setBillZip(String billZip) {
    Span span = tracer.spanBuilder("Domain: setBillZip").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billZip = billZip;
    } finally {
      span.end();
    }
  }

  public String getBillCountry() {
    Span span = tracer.spanBuilder("Domain: getBillCountry").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billCountry;
  }

  public void setBillCountry(String billCountry) {
    Span span = tracer.spanBuilder("Domain: setBillCountry").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billCountry = billCountry;
    } finally {
      span.end();
    }
  }

  public String getCourier() {
    Span span = tracer.spanBuilder("Domain: getCourier").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return courier;
  }

  public void setCourier(String courier) {
    Span span = tracer.spanBuilder("Domain: setCourier").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.courier = courier;
    } finally {
      span.end();
    }
  }

  public BigDecimal getTotalPrice() {
    Span span = tracer.spanBuilder("Domain: getTotalPrice").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    Span span = tracer.spanBuilder("Domain: setTotalPrice").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.totalPrice = totalPrice;
    } finally {
      span.end();
    }
  }

  public String getBillToFirstName() {
    Span span = tracer.spanBuilder("Domain: getBillToFirstName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billToFirstName;
  }

  public void setBillToFirstName(String billToFirstName) {
    Span span = tracer.spanBuilder("Domain: setBillToFirstName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billToFirstName = billToFirstName;
    } finally {
      span.end();
    }
  }

  public String getBillToLastName() {
    Span span = tracer.spanBuilder("Domain: getBillToLastName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return billToLastName;
  }

  public void setBillToLastName(String billToLastName) {
    Span span = tracer.spanBuilder("Domain: setBillToLastName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.billToLastName = billToLastName;
    } finally {
      span.end();
    }
  }

  public String getShipToFirstName() {
    Span span = tracer.spanBuilder("Domain: getShipToFirstName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipToFirstName;
  }

  public void setShipToFirstName(String shipFoFirstName) {
    Span span = tracer.spanBuilder("Domain: setShipToFirstName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipToFirstName = shipFoFirstName;
    } finally {
      span.end();
    }
  }

  public String getShipToLastName() {
    Span span = tracer.spanBuilder("Domain: getShipToLastName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return shipToLastName;
  }

  public void setShipToLastName(String shipToLastName) {
    Span span = tracer.spanBuilder("Domain: setShipToLastName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.shipToLastName = shipToLastName;
    } finally {
      span.end();
    }
  }

  public String getCreditCard() {
    Span span = tracer.spanBuilder("Domain: getCreditCard").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return creditCard;
  }

  public void setCreditCard(String creditCard) {
    Span span = tracer.spanBuilder("Domain: setCreditCard").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.creditCard = creditCard;
    } finally {
      span.end();
    }
  }

  public String getExpiryDate() {
    Span span = tracer.spanBuilder("Domain: getExpiryDate").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    Span span = tracer.spanBuilder("Domain: setExpiryDate").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.expiryDate = expiryDate;
    } finally {
      span.end();
    }
  }

  public String getCardType() {
    Span span = tracer.spanBuilder("Domain: getCardType").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return cardType;
  }

  public void setCardType(String cardType) {
    Span span = tracer.spanBuilder("Domain: setCardType").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.cardType = cardType;
    } finally {
      span.end();
    }
  }

  public String getLocale() {
    Span span = tracer.spanBuilder("Domain: getLocale").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return locale;
  }

  public void setLocale(String locale) {
    Span span = tracer.spanBuilder("Domain: setLocale").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.locale = locale;
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

  public void setLineItems(List<LineItem> lineItems) {
    Span span = tracer.spanBuilder("Domain: setLineItems").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.lineItems = lineItems;
    } finally {
      span.end();
    }
  }

  public List<LineItem> getLineItems() {
    Span span = tracer.spanBuilder("Domain: getLineItems").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
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
  public void initOrder(Account account, Cart cart) {
    Span span = tracer.spanBuilder("Domain: initOrder").startSpan();
    try (Scope ss = span.makeCurrent()) {
      username = account.getUsername();
      orderDate = new Date();

      shipToFirstName = account.getFirstName();
      shipToLastName = account.getLastName();
      shipAddress1 = account.getAddress1();
      shipAddress2 = account.getAddress2();
      shipCity = account.getCity();
      shipState = account.getState();
      shipZip = account.getZip();
      shipCountry = account.getCountry();

      billToFirstName = account.getFirstName();
      billToLastName = account.getLastName();
      billAddress1 = account.getAddress1();
      billAddress2 = account.getAddress2();
      billCity = account.getCity();
      billState = account.getState();
      billZip = account.getZip();
      billCountry = account.getCountry();

      totalPrice = cart.getSubTotal();

      creditCard = "999 9999 9999 9999";
      expiryDate = "12/03";
      cardType = "Visa";
      courier = "UPS";
      locale = "CA";
      status = "P";

      Iterator<CartItem> i = cart.getAllCartItems();
      while (i.hasNext()) {
        CartItem cartItem = i.next();
        addLineItem(cartItem);
      }

    } finally {
      span.end();
    }
  }

  public void addLineItem(CartItem cartItem) {
    Span span = tracer.spanBuilder("Domain: addLineItem (CartItem)").startSpan();
    try (Scope ss = span.makeCurrent()) {
      LineItem lineItem = new LineItem(lineItems.size() + 1, cartItem);
      addLineItem(lineItem);
    } finally {
      span.end();
    }
  }

  public void addLineItem(LineItem lineItem) {
    Span span = tracer.spanBuilder("Domain: addLineItem (LineItem)").startSpan();
    try (Scope ss = span.makeCurrent()) {
      lineItems.add(lineItem);
    } finally {
      span.end();
    }
  }

}
