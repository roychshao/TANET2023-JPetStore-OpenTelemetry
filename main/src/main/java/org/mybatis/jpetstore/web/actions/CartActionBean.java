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
import io.opentelemetry.context.Scope;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.mybatis.jpetstore.domain.Cart;
import org.mybatis.jpetstore.domain.CartItem;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Tracing;
import org.mybatis.jpetstore.service.CatalogService;

/**
 * The Class CartActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class CartActionBean extends AbstractActionBean {

  private static final long serialVersionUID = -4038684592582714235L;

  private static final String VIEW_CART = "/WEB-INF/jsp/cart/Cart.jsp";
  private static final String CHECK_OUT = "/WEB-INF/jsp/cart/Checkout.jsp";
  private transient final Tracer tracer = Tracing.getTracer();

  @SpringBean
  private transient CatalogService catalogService;

  private Cart cart = new Cart();
  private String workingItemId;

  public Cart getCart() {
    Span span = tracer.spanBuilder("ActionBean: getCart").startSpan();
    span.end();
    return cart;
  }

  public void setCart(Cart cart) {
    Span span = tracer.spanBuilder("ActionBean: setCart").startSpan();
    this.cart = cart;
    span.end();
  }

  public void setWorkingItemId(String workingItemId) {
    Span span = tracer.spanBuilder("ActionBean: setWorkingItemId").startSpan();
    this.workingItemId = workingItemId;
    span.end();
  }

  /**
   * Adds the item to cart.
   *
   * @return the resolution
   */
  public Resolution addItemToCart() {
    Span span = tracer.spanBuilder("ActionBean: addItemToCart").startSpan();
    try (Scope ss = span.makeCurrent()) {
      if (cart.containsItemId(workingItemId, span)) {
        cart.incrementQuantityByItemId(workingItemId, span);
      } else {
        // isInStock is a "real-time" property that must be updated
        // every time an item is added to the cart, even if other
        // item details are cached.
        boolean isInStock = catalogService.isItemInStock(workingItemId, span);
        Item item = catalogService.getItem(workingItemId, span);
        cart.addItem(item, isInStock, span);
      }
      span.end();
    }
    return new ForwardResolution(VIEW_CART);
  }

  /**
   * Removes the item from cart.
   *
   * @return the resolution
   */
  public Resolution removeItemFromCart() {
    Span span = tracer.spanBuilder("ActionBean: removeItemFromCart").startSpan();
    try (Scope ss = span.makeCurrent()) {
      Item item = cart.removeItemById(workingItemId, span);

      if (item == null) {
        setMessage("Attempted to remove null CartItem from Cart.");
        span.end();
        return new ForwardResolution(ERROR);
      } else {
        span.end();
        return new ForwardResolution(VIEW_CART);
      }
    }
  }

  /**
   * Update cart quantities.
   *
   * @return the resolution
   */
  public Resolution updateCartQuantities() {
    Span span = tracer.spanBuilder("ActionBean: updateCartQuantities").startSpan();
    try (Scope ss = span.makeCurrent()) {
      HttpServletRequest request = context.getRequest();

      Iterator<CartItem> cartItems = getCart().getAllCartItems(span);
      while (cartItems.hasNext()) {
        CartItem cartItem = cartItems.next();
        String itemId = cartItem.getItem(span).getItemId(span);
        try {
          int quantity = Integer.parseInt(request.getParameter(itemId));
          getCart().setQuantityByItemId(itemId, quantity, span);
          if (quantity < 1) {
            cartItems.remove();
          }
        } catch (Exception e) {
          // ignore parse exceptions on purpose
        }
      }
    } finally {
      span.end();
      return new ForwardResolution(VIEW_CART);
    }
  }

  public ForwardResolution viewCart() {
    Span span = tracer.spanBuilder("ActionBean: viewCart").startSpan();
    span.end();
    return new ForwardResolution(VIEW_CART);
  }

  public ForwardResolution checkOut() {
    Span span = tracer.spanBuilder("ActionBean: checkOut").startSpan();
    span.end();
    return new ForwardResolution(CHECK_OUT);
  }

  public void clear() {
    Span span = tracer.spanBuilder("ActionBean: clear").startSpan();
    try (Scope ss = span.makeCurrent()) {
      cart = new Cart();
      workingItemId = null;
    } finally {
      span.end();
    }
  }

}
