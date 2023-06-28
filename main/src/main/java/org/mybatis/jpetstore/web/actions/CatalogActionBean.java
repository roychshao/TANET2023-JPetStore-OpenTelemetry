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
  private transient Tracer tracer = Tracing.getTracer();

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
    Span span = tracer.spanBuilder("ActionBean: getKeyword").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return keyword;
  }

  public void setKeyword(String keyword) {
    Span span = tracer.spanBuilder("ActionBean: setKeyword").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.keyword = keyword;
    } finally {
      span.end();
    }
  }

  public String getCategoryId() {
    Span span = tracer.spanBuilder("ActionBean: getCategoryId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    Span span = tracer.spanBuilder("ActionBean: setCategoryId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.categoryId = categoryId;
    } finally {
      span.end();
    }
  }

  public String getProductId() {
    Span span = tracer.spanBuilder("ActionBean: getProductId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return productId;
  }

  public void setProductId(String productId) {
    Span span = tracer.spanBuilder("ActionBean: setProductId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.productId = productId;
    } finally {
      span.end();
    }
  }

  public String getItemId() {
    Span span = tracer.spanBuilder("ActionBean: getItemId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return itemId;
  }

  public void setItemId(String itemId) {
    Span span = tracer.spanBuilder("ActionBean: setItemId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.itemId = itemId;
    } finally {
      span.end();
    }
  }

  public Category getCategory() {
    Span span = tracer.spanBuilder("ActionBean: getCategoryd").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return category;
  }

  public void setCategory(Category category) {
    Span span = tracer.spanBuilder("ActionBean: setCategory").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.category = category;
    } finally {
      span.end();
    }
  }

  public Product getProduct() {
    Span span = tracer.spanBuilder("ActionBean: getProduct").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return product;
  }

  public void setProduct(Product product) {
    Span span = tracer.spanBuilder("ActionBean: setProduct").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.product = product;
    } finally {
      span.end();
    }
  }

  public Item getItem() {
    Span span = tracer.spanBuilder("ActionBean: getItem").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return item;
  }

  public void setItem(Item item) {
    Span span = tracer.spanBuilder("ActionBean: setItem").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.item = item;
    } finally {
      span.end();
    }
  }

  public List<Category> getCategoryList() {
    Span span = tracer.spanBuilder("ActionBean: getCategoryList").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return categoryList;
  }

  public void setCategoryList(List<Category> categoryList) {
    Span span = tracer.spanBuilder("ActionBean: setCategoryList").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.categoryList = categoryList;
    } finally {
      span.end();
    }
  }

  public List<Product> getProductList() {
    Span span = tracer.spanBuilder("ActionBean: getProductList").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return productList;
  }

  public void setProductList(List<Product> productList) {
    Span span = tracer.spanBuilder("ActionBean: setProductList").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.productList = productList;
    } finally {
      span.end();
    }
  }

  public List<Item> getItemList() {
    Span span = tracer.spanBuilder("ActionBean: getItemList").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return itemList;
  }

  public void setItemList(List<Item> itemList) {
    Span span = tracer.spanBuilder("ActionBean: setItemList").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.itemList = itemList;
    } finally {
      span.end();
    }
  }

  @DefaultHandler
  public ForwardResolution viewMain() {
    Span span = tracer.spanBuilder("ActionBean: viewMain").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return new ForwardResolution(MAIN);
  }

  /**
   * View category.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewCategory() {
    Span span = tracer.spanBuilder("ActionBean: viewCategory").startSpan();
    try (Scope ss = span.makeCurrent()) {
      if (categoryId != null) {
        productList = catalogService.getProductListByCategory(categoryId);
        category = catalogService.getCategory(categoryId);
      }
    } finally {
      span.end();
    }
    return new ForwardResolution(VIEW_CATEGORY);
  }

  /**
   * View product.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewProduct() {
    Span span = tracer.spanBuilder("ActionBean: viewProduct").startSpan();
    try (Scope ss = span.makeCurrent()) {
      if (productId != null) {
        itemList = catalogService.getItemListByProduct(productId);
        product = catalogService.getProduct(productId);
      }
    } finally {
      span.end();
    }
    return new ForwardResolution(VIEW_PRODUCT);
  }

  /**
   * View item.
   *
   * @return the forward resolution
   */
  public ForwardResolution viewItem() {
    Span span = tracer.spanBuilder("ActionBean: viewItem").startSpan();
    try (Scope ss = span.makeCurrent()) {
      item = catalogService.getItem(itemId);
      product = item.getProduct();
    } finally {
      span.end();
    }
    return new ForwardResolution(VIEW_ITEM);
  }

  /**
   * Search products.
   *
   * @return the forward resolution
   */
  public ForwardResolution searchProducts() {
    Span span = tracer.spanBuilder("ActionBean: searchProducts").startSpan();
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
    Span span = tracer.spanBuilder("ActionBean: clear").startSpan();
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
