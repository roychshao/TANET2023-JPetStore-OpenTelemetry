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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Product;
import org.mybatis.jpetstore.domain.Tracing;
import org.mybatis.jpetstore.service.AccountService;
import org.mybatis.jpetstore.service.CatalogService;

/**
 * The Class AccountActionBean.
 *
 * @author Eduardo Macarron
 */
@SessionScope
public class AccountActionBean extends AbstractActionBean {

  private static final long serialVersionUID = 5499663666155758178L;

  private static final String NEW_ACCOUNT = "/WEB-INF/jsp/account/NewAccountForm.jsp";
  private static final String EDIT_ACCOUNT = "/WEB-INF/jsp/account/EditAccountForm.jsp";
  private static final String SIGNON = "/WEB-INF/jsp/account/SignonForm.jsp";

  private static final List<String> LANGUAGE_LIST;
  private static final List<String> CATEGORY_LIST;
  private transient final Tracer tracer = Tracing.getTracer();

  @SpringBean
  private transient AccountService accountService;
  @SpringBean
  private transient CatalogService catalogService;

  private Account account = new Account();
  private List<Product> myList;
  private boolean authenticated;

  static {
    LANGUAGE_LIST = Collections.unmodifiableList(Arrays.asList("english", "japanese"));
    CATEGORY_LIST = Collections.unmodifiableList(Arrays.asList("FISH", "DOGS", "REPTILES", "CATS", "BIRDS"));
  }

  public Account getAccount() {
    Span span = tracer.spanBuilder("ActionBean: getAccount").startSpan();
    span.end();
    return this.account;
  }

  public String getUsername() {
    Span span = tracer.spanBuilder("ActionBean: getUsername").startSpan();
    String result = account.getUsername(span);
    span.end();
    return result;
  }

  @Validate(required = true, on = { "signon", "newAccount", "editAccount" })
  public void setUsername(String username) {
    Span span = tracer.spanBuilder("ActionBean: setUsername").startSpan();
    try (Scope ss = span.makeCurrent()) {
      account.setUsername(username, span);
    } finally {
      span.end();
    }
  }

  public String getPassword() {
    Span span = tracer.spanBuilder("ActionBean: getPassword").startSpan();
    span.end();
    String result = account.getPassword(span);
    return result;
  }

  @Validate(required = true, on = { "signon", "newAccount", "editAccount" })
  public void setPassword(String password) {
    Span span = tracer.spanBuilder("ActionBeanL setPassword").startSpan();
    try (Scope ss = span.makeCurrent()) {
      account.setPassword(password, span);
    } finally {
      span.end();
    }
  }

  public List<Product> getMyList() {
    Span span = tracer.spanBuilder("ActionBean: getMyList").startSpan();
    span.end();
    return myList;
  }

  public void setMyList(List<Product> myList) {
    Span span = tracer.spanBuilder("ActionBean: setMyList").startSpan();
    this.myList = myList;
    span.end();
  }

  public List<String> getLanguages() {
    Span span = tracer.spanBuilder("ActionBean: getLanguages").startSpan();
    span.end();
    return LANGUAGE_LIST;
  }

  public List<String> getCategories() {
    Span span = tracer.spanBuilder("ActionBean: getCategories").startSpan();
    span.end();
    return CATEGORY_LIST;
  }

  public Resolution newAccountForm() {
    Span span = tracer.spanBuilder("ActionBean: newAccountForm").startSpan();
    span.end();
    return new ForwardResolution(NEW_ACCOUNT);
  }

  /**
   * New account.
   *
   * @return the resolution
   */
  public Resolution newAccount() {
    Span span = tracer.spanBuilder("ActionBean: newAccount").startSpan();
    try (Scope ss = span.makeCurrent()) {
      accountService.insertAccount(account);
      account = accountService.getAccount(account.getUsername(span));
      myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId(span), span);
      authenticated = true;
    } finally {
      span.end();
      return new RedirectResolution(CatalogActionBean.class);
    }
  }

  /**
   * Edits the account form.
   *
   * @return the resolution
   */
  public Resolution editAccountForm() {
    Span span = tracer.spanBuilder("ActionBean: editAccountForm").startSpan();
    span.end();
    return new ForwardResolution(EDIT_ACCOUNT);
  }

  /**
   * Edits the account.
   *
   * @return the resolution
   */
  public Resolution editAccount() {
    Span span = tracer.spanBuilder("ActionBean: editAccount").startSpan();
    try (Scope ss = span.makeCurrent()) {
      accountService.updateAccount(account);
      account = accountService.getAccount(account.getUsername(span));
      myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId(span), span);
    } finally {
      span.end();
      return new RedirectResolution(CatalogActionBean.class);
    }
  }

  /**
   * Signon form.
   *
   * @return the resolution
   */
  @DefaultHandler
  public Resolution signonForm() {
    Span span = tracer.spanBuilder("ActionBean: signonForm").startSpan();
    span.end();
    return new ForwardResolution(SIGNON);
  }

  /**
   * Signon.
   *
   * @return the resolution
   */
  public Resolution signon() {
    Span span = tracer.spanBuilder("ActionBean: signon").startSpan();
    try (Scope ss = span.makeCurrent()) {
      account = accountService.getAccount(getUsername(), getPassword(), span);

      if (account == null) {
        String value = "Invalid username or password.  Signon failed.";
        setMessage(value);
        clear(span);
        span.end();
        return new ForwardResolution(SIGNON);
      } else {
        account.setPassword(null, span);
        myList = catalogService.getProductListByCategory(account.getFavouriteCategoryId(span), span);
        authenticated = true;
        HttpSession s = context.getRequest().getSession();
        // this bean is already registered as /actions/Account.action
        s.setAttribute("accountBean", this);
        span.end();
        return new RedirectResolution(CatalogActionBean.class);
      }
    }
  }

  /**
   * Signoff.
   *
   * @return the resolution
   */
  public Resolution signoff() {
    Span span = tracer.spanBuilder("ActionBean: signoff").startSpan();
    try (Scope ss = span.makeCurrent()) {
      context.getRequest().getSession().invalidate();
      clear(span);
    } finally {
      span.end();
      return new RedirectResolution(CatalogActionBean.class);
    }
  }

  /**
   * Checks if is authenticated.
   *
   * @return true, if is authenticated
   */
  public boolean isAuthenticated() {
    Span span = tracer.spanBuilder("ActionBean: isAuthenticated").startSpan();
    span.end();
    return authenticated && account != null && account.getUsername(span) != null;
  }

  /**
   * Clear.
   */
  public void clear(Span parentSpan) {
    Span span = tracer.spanBuilder("ActionBean: clear").setParent(Context.current().with(parentSpan)).startSpan();
    account = new Account();
    myList = null;
    authenticated = false;
    span.end();
  }

}
