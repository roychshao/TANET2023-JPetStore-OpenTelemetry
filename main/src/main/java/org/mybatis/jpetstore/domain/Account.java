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

import net.sourceforge.stripes.validation.Validate;

/**
 * The Class Account.
 *
 * @author Eduardo Macarron
 */
public class Account implements Serializable {

  private static final long serialVersionUID = 8751282105532159742L;

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String status;
  private String address1;
  private String address2;
  private String city;
  private String state;
  private String zip;
  private String country;
  private String phone;
  private String favouriteCategoryId;
  private String languagePreference;
  private boolean listOption;
  private boolean bannerOption;
  private String bannerName;
  private transient final Tracer tracer = Tracing.getTracer();

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

  public String getPassword() {
    Span span = tracer.spanBuilder("Domain: getPassword").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return password;
  }

  public void setPassword(String password) {
    Span span = tracer.spanBuilder("Domain: setPassword").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.password = password;
    } finally {
      span.end();
    }
  }

  public String getEmail() {
    Span span = tracer.spanBuilder("Domain: getEmail").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return email;
  }

  public void setEmail(String email) {
    Span span = tracer.spanBuilder("Domain: setEmail").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.email = email;
    } finally {
      span.end();
    }
  }

  public String getFirstName() {
    Span span = tracer.spanBuilder("Domain: getFirstName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return firstName;
  }

  @Validate(required = true, on = { "newAccount", "editAccount" })
  public void setFirstName(String firstName) {
    Span span = tracer.spanBuilder("Domain: setFirstName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.firstName = firstName;
    } finally {
      span.end();
    }
  }

  public String getLastName() {
    Span span = tracer.spanBuilder("Domain: getLastName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return lastName;
  }

  @Validate(required = true, on = { "newAccount", "editAccount" })
  public void setLastName(String lastName) {
    Span span = tracer.spanBuilder("Domain: setLastName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.lastName = lastName;
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

  public String getAddress1() {
    Span span = tracer.spanBuilder("Domain: getAddress1").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return address1;
  }

  public void setAddress1(String address1) {
    Span span = tracer.spanBuilder("Domain: setAddress1").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.address1 = address1;
    } finally {
      span.end();
    }
  }

  public String getAddress2() {
    Span span = tracer.spanBuilder("Domain: getAddress2").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return address2;
  }

  public void setAddress2(String address2) {
    Span span = tracer.spanBuilder("Domain: setAddress2").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.address2 = address2;
    } finally {
      span.end();
    }
  }

  public String getCity() {
    Span span = tracer.spanBuilder("Domain: getCity").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return city;
  }

  public void setCity(String city) {
    Span span = tracer.spanBuilder("Domain: setCity").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.city = city;
    } finally {
      span.end();
    }
  }

  public String getState() {
    Span span = tracer.spanBuilder("Domain: getState").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return state;
  }

  public void setState(String state) {
    Span span = tracer.spanBuilder("Domain: setState").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.state = state;
    } finally {
      span.end();
    }
  }

  public String getZip() {
    Span span = tracer.spanBuilder("Domain: getZip").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return zip;
  }

  public void setZip(String zip) {
    Span span = tracer.spanBuilder("Domain: setZip").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.zip = zip;
    } finally {
      span.end();
    }
  }

  public String getCountry() {
    Span span = tracer.spanBuilder("Domain: getCountry").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return country;
  }

  public void setCountry(String country) {
    Span span = tracer.spanBuilder("Domain: setCountry").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.country = country;
    } finally {
      span.end();
    }
  }

  public String getPhone() {
    Span span = tracer.spanBuilder("Domain: getPhone").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return phone;
  }

  public void setPhone(String phone) {
    Span span = tracer.spanBuilder("Domain: setPhone").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.phone = phone;
    } finally {
      span.end();
    }
  }

  public String getFavouriteCategoryId() {
    Span span = tracer.spanBuilder("Domain: getFavouriteCategoryId").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return favouriteCategoryId;
  }

  public void setFavouriteCategoryId(String favouriteCategoryId) {
    Span span = tracer.spanBuilder("Domain: setFavouriteCategoryId").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.favouriteCategoryId = favouriteCategoryId;
    } finally {
      span.end();
    }
  }

  public String getLanguagePreference() {
    Span span = tracer.spanBuilder("Domain: getLanguagePreference").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return languagePreference;
  }

  public void setLanguagePreference(String languagePreference) {
    Span span = tracer.spanBuilder("Domain: setLanguagePreference").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.languagePreference = languagePreference;
    } finally {
      span.end();
    }
  }

  public boolean isListOption() {
    Span span = tracer.spanBuilder("Domain: isListOption").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return listOption;
  }

  public void setListOption(boolean listOption) {
    Span span = tracer.spanBuilder("Domain: setListOption").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.listOption = listOption;
    } finally {
      span.end();
    }
  }

  public boolean isBannerOption() {
    Span span = tracer.spanBuilder("Domain: isBannerOption").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return bannerOption;
  }

  public void setBannerOption(boolean bannerOption) {
    Span span = tracer.spanBuilder("Domain: setBannerOption").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.bannerOption = bannerOption;
    } finally {
      span.end();
    }
  }

  public String getBannerName() {
    Span span = tracer.spanBuilder("Domain: getBannerName").startSpan();
    try (Scope ss = span.makeCurrent()) {
    } finally {
      span.end();
    }
    return bannerName;
  }

  public void setBannerName(String bannerName) {
    Span span = tracer.spanBuilder("Domain: setBannerName").startSpan();
    try (Scope ss = span.makeCurrent()) {
      this.bannerName = bannerName;
    } finally {
      span.end();
    }
  }

}
