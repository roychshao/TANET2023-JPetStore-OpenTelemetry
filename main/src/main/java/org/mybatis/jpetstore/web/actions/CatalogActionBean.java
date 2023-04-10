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
import io.opentelemetry.context.Scope;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.mybatis.jpetstore.domain.Category;
import org.mybatis.jpetstore.domain.Item;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.domain.Tracing;
import org.mybatis.jpetstore.service.CatalogService;

/**
 * The Class CatalogActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class CatalogActionBean extends AbstractActionBean {

  private static final long serialVersionUID = 5849523372175050635L;

  private static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";
  private static final String VIEW_CATEGORY = "/WEB-INF/jsp/catalog/Category.jsp";
  private static final String VIEW_PRODUCT = "/WEB-INF/jsp/catalog/Product.jsp";
  private static final String VIEW_ITEM = "/WEB-INF/jsp/catalog/Item.jsp";
  private static final String SEARCH_PRODUCTS = "/WEB-INF/jsp/catalog/SearchProducts.jsp";
  private transient final Tracer tracer = Tracing.getTracer();
  private transient final Context rootContext = Tracing.getRootContext();

  @SpringBean
  private transient CatalogService catalogService;

  private String keyword;

  private String categoryId;
  private Category category;
  private List<Category> categoryList;

  private String productId;
  private Product product;
  private List<Product> productList;

  private String itemId;
  private Item item;
  private List<Item> itemList;

  public String getKeyword() {
    Span span = tracer.spanBuilder("ActionBean: getKeyword").setParent(rootContext).startSpan();
    span.end();
    return keyword;
  }

  public void setKeyword(String keyword) {
    Span span = tracer.spanBuilder("ActionBean: setKeyword").setParent(rootContext).startSpan();
    this.keyword = keyword;
    span.end();
  }

  public String getCategoryId() {
    Span span = tracer.spanBuilder("ActionBean: getCategoryId").setParent(rootContext).startSpan();
    span.end();
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    Span span = tracer.spanBuilder("ActionBean: setCategoryId").setParent(rootContext).startSpan();
    this.categoryId = categoryId;
    span.end();
  }

  public String getProductId() {
    Span span = tracer.spanBuilder("ActionBean: getProductId").setParent(rootContext).startSpan();
    span.end();
    return productId;
  }

  public void setProductId(String productId) {
    Span span = tracer.spanBuilder("ActionBean: setProductId").setParent(rootContext).startSpan();
    this.productId = productId;
    span.end();
  }

  public String getItemId() {
    Span span = tracer.spanBuilder("ActionBean: getItemId").setParent(rootContext).startSpan();
    span.end();
    return itemId;
  }

  public void setItemId(String itemId) {
    Span span = tracer.spanBuilder("ActionBean: setItemId").setParent(rootContext).startSpan();
    this.itemId = itemId;
    span.end();
  }

  public Category getCategory() {
    Span span = tracer.spanBuilder("ActionBean: getCategoryd").setParent(rootContext).startSpan();
    span.end();
    return category;
  }

  public void setCategory(Category category) {
    Span span = tracer.spanBuilder("ActionBean: setCategory").setParent(rootContext).startSpan();
    this.category = category;
    span.end();
  }

  public Product getProduct() {
    Span span = tracer.spanBuilder("ActionBean: getProduct").setParent(rootContext).startSpan();
    span.end();
    return product;
  }

  public void setProduct(Product product) {
    Span span = tracer.spanBuilder("ActionBean: setProduct").setParent(rootContext).startSpan();
    this.product = product;
    span.end();
  }

  public Item getItem() {
    Span span = tracer.spanBuilder("ActionBean: getItem").setParent(rootContext).startSpan();
    span.end();
    return item;
  }

  public void setItem(Item item) {
    Span span = tracer.spanBuilder("ActionBean: setItem").setParent(rootContext).startSpan();
    this.item = item;
    span.end();
  }

  public List<Category> getCategoryList() {
    Span span = tracer.spanBuilder("ActionBean: getCategoryList").setParent(rootContext).startSpan();
    span.end();
    return categoryList;
  }

  public void setCategoryList(List<Category> categoryList) {
    Span span = tracer.spanBuilder("ActionBean: setCategoryList").setParent(rootContext).startSpan();
    this.categoryList = categoryList;
    span.end();
  }

  public List<Product> getProductList() {
    Span span = tracer.spanBuilder("ActionBean: getProductList").setParent(rootContext).startSpan();
    span.end();
    return productList;
  }

  public void setProductList(List<Product> productList) {
    Span span = tracer.spanBuilder("ActionBean: setProductList").setParent(rootContext).startSpan();
    this.productList = productList;
    span.end();
  }

  public List<Item> getItemList() {
    Span span = tracer.spanBuilder("ActionBean: getItemList").setParent(rootContext).startSpan();
    span.end();
    return itemList;
  }

  public void setItemList(List<Item> itemList) {
    Span span = tracer.spanBuilder("ActionBean: setItemList").setParent(rootContext).startSpan();
    this.itemList = itemList;
    span.end();
  }

  @DefaultHandler
  public ForwardResolution viewMain() {
    Span span = tracer.spanBuilder("ActionBean: viewMain").setParent(rootContext).startSpan();
    span.end();
    return new ForwardResolution(MAIN);
  }

  /**
   * View category.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewCategory() {
    Span span = tracer.spanBuilder("ActionBean: viewCategory").setParent(rootContext).startSpan();
    try (Scope ss = span.makeCurrent()) {
      if (categoryId != null) {
        productList = catalogService.getProductListByCategory(categoryId, span);
        category = catalogService.getCategory(categoryId, span);
      }
    } finally {
      span.end();
      return new ForwardResolution(VIEW_CATEGORY);
    }
  }

  /**
   * View product.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewProduct() {
    Span span = tracer.spanBuilder("ActionBean: viewProduct").setParent(rootContext).startSpan();
    try (Scope ss = span.makeCurrent()) {
      if (productId != null) {
        itemList = catalogService.getItemListByProduct(productId);
        product = catalogService.getProduct(productId);
      }
    } finally {
      span.end();
      return new ForwardResolution(VIEW_PRODUCT);
    }
  }

  /**
   * View item.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewItem() {
    Span span = tracer.spanBuilder("ActionBean: viewItem").setParent(rootContext).startSpan();
    try (Scope ss = span.makeCurrent()) {
      item = catalogService.getItem(itemId, span);
      product = item.getProduct(span);
    } finally {
      span.end();
      return new ForwardResolution(VIEW_ITEM);
    }
  }

  /**
   * Search products.
   *
   * @return the forward resolution
   */
  public ForwardResolution searchProducts() {
    Span span = tracer.spanBuilder("ActionBean: searchProducts").setParent(rootContext).startSpan();
    try (Scope ss = span.makeCurrent()) {
      if (keyword == null || keyword.length() < 1) {
        setMessage("Please enter a keyword to search for, then press the search button.");
        span.end();
        return new ForwardResolution(ERROR);
      } else {
        productList = catalogService.searchProductList(keyword.toLowerCase());
        span.end();
        return new ForwardResolution(SEARCH_PRODUCTS);
      }
    }
  }

  /**
   * Clear.
   */
  public void clear() {
    Span span = tracer.spanBuilder("ActionBean: clear").setParent(rootContext).startSpan();
    try (Scope ss = span.makeCurrent()) {
      keyword = null;

      categoryId = null;
      category = null;
      categoryList = null;

      productId = null;
      product = null;
      productList = null;

      itemId = null;
      item = null;
      itemList = null;
    } finally {
      span.end();
    }
  }

}
