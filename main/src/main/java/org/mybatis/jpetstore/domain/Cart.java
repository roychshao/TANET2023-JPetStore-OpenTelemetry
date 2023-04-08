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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The Class Cart.
 *
 * @author Eduardo Macarron
 */
public class Cart implements Serializable {

  private static final long serialVersionUID = 8329559983943337176L;

  private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<>());
  private final List<CartItem> itemList = new ArrayList<>();
  private transient final Tracer tracer = Tracing.getTracer();

  public Iterator<CartItem> getCartItems(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getCartItems").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return itemList.iterator();
  }

  public List<CartItem> getCartItemList(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getCartItemList").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return itemList;
  }

  public int getNumberOfItems(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getNumberOfItems").setParent(Context.current().with(parentSpan))
        .startSpan();
    span.end();
    return itemList.size();
  }

  public Iterator<CartItem> getAllCartItems(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getAllCartItems").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return itemList.iterator();
  }

  public boolean containsItemId(String itemId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: containsItemId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return itemMap.containsKey(itemId);
  }

  /**
   * Adds the item.
   *
   * @param item
   *          the item
   * @param isInStock
   *          the is in stock
   */
  public void addItem(Item item, boolean isInStock, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: addItem").setParent(Context.current().with(parentSpan)).startSpan();
    CartItem cartItem = itemMap.get(item.getItemId(span));
    if (cartItem == null) {
      cartItem = new CartItem();
      cartItem.setItem(item, span);
      cartItem.setQuantity(0, span);
      cartItem.setInStock(isInStock, span);
      itemMap.put(item.getItemId(span), cartItem);
      itemList.add(cartItem);
    }
    cartItem.incrementQuantity(span);
    span.end();
  }

  /**
   * Removes the item by id.
   *
   * @param itemId
   *          the item id
   *
   * @return the item
   */
  public Item removeItemById(String itemId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: removeItemById").setParent(Context.current().with(parentSpan)).startSpan();
    CartItem cartItem = itemMap.remove(itemId);
    if (cartItem == null) {
      span.end();
      return null;
    } else {
      itemList.remove(cartItem);
      Item result = cartItem.getItem(span);
      span.end();
      return result;
    }
  }

  /**
   * Increment quantity by item id.
   *
   * @param itemId
   *          the item id
   */
  public void incrementQuantityByItemId(String itemId, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: incrementQuantityByItemId").setParent(Context.current().with(parentSpan))
        .startSpan();
    CartItem cartItem = itemMap.get(itemId);
    cartItem.incrementQuantity(span);
    span.end();
  }

  public void setQuantityByItemId(String itemId, int quantity, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setQuantityByItemId").setParent(Context.current().with(parentSpan))
        .startSpan();
    CartItem cartItem = itemMap.get(itemId);
    cartItem.setQuantity(quantity, span);
    span.end();
  }

  /**
   * Gets the sub total.
   *
   * @return the sub total
   */
  public BigDecimal getSubTotal(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getSubTotal").setParent(Context.current().with(parentSpan)).startSpan();
    BigDecimal result = itemList.stream()
        .map(cartItem -> cartItem.getItem(span).getListPrice(span).multiply(new BigDecimal(cartItem.getQuantity(span))))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    span.end();
    return result;
  }

}
