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
package org.mybatis.jpetstore.service;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Order;
import org.mybatis.jpetstore.domain.Sequence;
import org.mybatis.jpetstore.domain.Tracing;
import org.mybatis.jpetstore.mapper.ItemMapper;
import org.mybatis.jpetstore.mapper.LineItemMapper;
import org.mybatis.jpetstore.mapper.OrderMapper;
import org.mybatis.jpetstore.mapper.SequenceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class OrderService.
 *
 * @author Eduardo Macarron
 */
@Service
public class OrderService {

  private final ItemMapper itemMapper;
  private final OrderMapper orderMapper;
  private final SequenceMapper sequenceMapper;
  private final LineItemMapper lineItemMapper;
  private transient final Tracer tracer = Tracing.getTracer();

  public OrderService(ItemMapper itemMapper, OrderMapper orderMapper, SequenceMapper sequenceMapper,
      LineItemMapper lineItemMapper) {
    this.itemMapper = itemMapper;
    this.orderMapper = orderMapper;
    this.sequenceMapper = sequenceMapper;
    this.lineItemMapper = lineItemMapper;
  }

  /**
   * Insert order.
   *
   * @param order
   *          the order
   */
  @Transactional
  public void insertOrder(Order order, Span parentSpan) {
    Span span = tracer.spanBuilder("Service: insertOrder").setParent(Context.current().with(parentSpan)).startSpan();
    order.setOrderId(getNextId("ordernum"), span);
    order.getLineItems(span).forEach(lineItem -> {
      String itemId = lineItem.getItemId(span);
      Integer increment = lineItem.getQuantity(span);
      Map<String, Object> param = new HashMap<>(2);
      param.put("itemId", itemId);
      param.put("increment", increment);
      itemMapper.updateInventoryQuantity(param);
    });

    orderMapper.insertOrder(order);
    orderMapper.insertOrderStatus(order);
    order.getLineItems(span).forEach(lineItem -> {
      lineItem.setOrderId(order.getOrderId(span), span);
      lineItemMapper.insertLineItem(lineItem);
    });
    span.end();
  }

  /**
   * Gets the order.
   *
   * @param orderId
   *          the order id
   *
   * @return the order
   */
  @Transactional
  public Order getOrder(int orderId, Span parentSpan) {
    Span span = tracer.spanBuilder("Service: getOrder").setParent(Context.current().with(parentSpan)).startSpan();
    Order order = orderMapper.getOrder(orderId);
    order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId), span);

    order.getLineItems(span).forEach(lineItem -> {
      Item item = itemMapper.getItem(lineItem.getItemId(span));
      item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId(span)), span);
      lineItem.setItem(item, span);
    });

    return order;
  }

  /**
   * Gets the orders by username.
   *
   * @param username
   *          the username
   *
   * @return the orders by username
   */
  public List<Order> getOrdersByUsername(String username, Span parentSpan) {
    Span span = tracer.spanBuilder("Service: getOrdersByUsername").setParent(Context.current().with(parentSpan))
        .startSpan();
    span.end();
    return orderMapper.getOrdersByUsername(username);
  }

  /**
   * Gets the next id.
   *
   * @param name
   *          the name
   *
   * @return the next id
   */
  public int getNextId(String name) {
    Sequence sequence = sequenceMapper.getSequence(new Sequence(name, -1));
    if (sequence == null) {
      throw new RuntimeException(
          "Error: A null sequence was returned from the database (could not get next " + name + " sequence).");
    }
    Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
    sequenceMapper.updateSequence(parameterObject);
    return sequence.getNextId();
  }

}
