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
package org.mybatis.jpetstore.web.actions;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.domain.Tracing;
import org.mybatis.jpetstore.service.OrderService;

/**
 * The Class OrderActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class OrderActionBean extends AbstractActionBean {

  private static final long serialVersionUID = -6171288227470176272L;

  private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/ConfirmOrder.jsp";
  private static final String LIST_ORDERS = "/WEB-INF/jsp/order/ListOrders.jsp";
  private static final String NEW_ORDER = "/WEB-INF/jsp/order/NewOrderForm.jsp";
  private static final String SHIPPING = "/WEB-INF/jsp/order/ShippingForm.jsp";
  private static final String VIEW_ORDER = "/WEB-INF/jsp/order/ViewOrder.jsp";

  private static final List<String> CARD_TYPE_LIST;

  @SpringBean
  private transient OrderService orderService;

  private Order order = new Order();
  private boolean shippingAddressRequired;
  private boolean confirmed;
  private List<Order> orderList;
  private transient final Tracer tracer = Tracing.getTracer();
  private transient final Context rootContext = Tracing.getRootContext();

  static {
    CARD_TYPE_LIST = Collections.unmodifiableList(Arrays.asList("Visa", "MasterCard", "American Express"));
  }

  public int getOrderId() {
    Span span = tracer.spanBuilder("ActionBean: getOrderId").setParent(rootContext).startSpan();
    int result = 0;
    result = order.getOrderId(span);
    span.end();
    return result;
  }

  public void setOrderId(int orderId) {
    Span span = tracer.spanBuilder("ActionBean: setOrderId").setParent(rootContext).startSpan();
    order.setOrderId(orderId, span);
    span.end();
  }

  public Order getOrder() {
    Span span = tracer.spanBuilder("ActionBean: getOrder").setParent(rootContext).startSpan();
    span.end();
    return order;
  }

  public void setOrder(Order order) {
    Span span = tracer.spanBuilder("ActionBean: setOrder").setParent(rootContext).startSpan();
    this.order = order;
    span.end();
  }

  public boolean isShippingAddressRequired() {
    Span span = tracer.spanBuilder("ActionBean: isShippingAddressRequired").setParent(rootContext).startSpan();
    span.end();
    return shippingAddressRequired;
  }

  public void setShippingAddressRequired(boolean shippingAddressRequired) {
    Span span = tracer.spanBuilder("ActionBean: setShippingAddressRequired").setParent(rootContext).startSpan();
    this.shippingAddressRequired = shippingAddressRequired;
    span.end();
  }

  public boolean isConfirmed() {
    Span span = tracer.spanBuilder("ActionBean: isConfirmed").setParent(rootContext).startSpan();
    span.end();
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    Span span = tracer.spanBuilder("ActionBean: setConfirmed").setParent(rootContext).startSpan();
    this.confirmed = confirmed;
    span.end();
  }

  public List<String> getCreditCardTypes() {
    Span span = tracer.spanBuilder("ActionBean: getCreditCardTypes").setParent(rootContext).startSpan();
    span.end();
    return CARD_TYPE_LIST;
  }

  public List<Order> getOrderList() {
    Span span = tracer.spanBuilder("ActionBean: getOrderList").setParent(rootContext).startSpan();
    span.end();
    return orderList;
  }

  /**
   * List orders.
   *
   * @return the resolution
   */
  public Resolution listOrders() {
    Span span = tracer.spanBuilder("ActionBean: listOrders").setParent(rootContext).startSpan();
    HttpSession session = context.getRequest().getSession();
    AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
    orderList = orderService.getOrdersByUsername(accountBean.getAccount().getUsername(span), span);
    span.end();
    return new ForwardResolution(LIST_ORDERS);
  }

  /**
   * New order form.
   *
   * @return the resolution
   */
  public Resolution newOrderForm() {
    Span span = tracer.spanBuilder("ActionBean: newOrderForm").setParent(rootContext).startSpan();
    HttpSession session = context.getRequest().getSession();
    AccountActionBean accountBean = (AccountActionBean) session.getAttribute("/actions/Account.action");
    CartActionBean cartBean = (CartActionBean) session.getAttribute("/actions/Cart.action");

    clear();
    if (accountBean == null || !accountBean.isAuthenticated()) {
      setMessage("You must sign on before attempting to check out.  Please sign on and try checking out again.");
      span.end();
      return new ForwardResolution(AccountActionBean.class);
    } else if (cartBean != null) {
      order.initOrder(accountBean.getAccount(), cartBean.getCart(), span);
      span.end();
      return new ForwardResolution(NEW_ORDER);
    } else {
      setMessage("An order could not be created because a cart could not be found.");
      span.end();
      return new ForwardResolution(ERROR);
    }
  }

  /**
   * New order.
   *
   * @return the resolution
   */
  public Resolution newOrder() {
    Span span = tracer.spanBuilder("ActionBean: newOrder").setParent(rootContext).startSpan();
    HttpSession session = context.getRequest().getSession();

    if (shippingAddressRequired) {
      shippingAddressRequired = false;
      return new ForwardResolution(SHIPPING);
    } else if (!isConfirmed()) {
      return new ForwardResolution(CONFIRM_ORDER);
    } else if (getOrder() != null) {

      orderService.insertOrder(order, span);

      CartActionBean cartBean = (CartActionBean) session.getAttribute("/actions/Cart.action");
      cartBean.clear();

      setMessage("Thank you, your order has been submitted.");

      span.end();
      return new ForwardResolution(VIEW_ORDER);
    } else {
      setMessage("An error occurred processing your order (order was null).");
      span.end();
      return new ForwardResolution(ERROR);
    }
  }

  /**
   * View order.
   *
   * @return the resolution
   */
  public Resolution viewOrder() {
    Span span = tracer.spanBuilder("ActionBean: viewOrder").setParent(rootContext).startSpan();
    HttpSession session = context.getRequest().getSession();

    AccountActionBean accountBean = (AccountActionBean) session.getAttribute("accountBean");

    order = orderService.getOrder(order.getOrderId(span), span);

    if (accountBean.getAccount().getUsername(span).equals(order.getUsername(span))) {
      span.end();
      return new ForwardResolution(VIEW_ORDER);
    } else {
      order = null;
      setMessage("You may only view your own orders.");
      span.end();
      return new ForwardResolution(ERROR);
    }
  }

  /**
   * Clear.
   */
  public void clear() {
    Span span = tracer.spanBuilder("ActionBean: clear").setParent(rootContext).startSpan();
    order = new Order();
    shippingAddressRequired = false;
    confirmed = false;
    orderList = null;
    span.end();
  }

}
