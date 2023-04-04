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

  public String getPassword(Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: getPassword").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return password;
  }

  public void setPassword(String password, Span parentSpan) {
    Span span = tracer.spanBuilder("Domain: setPassword").setParent(Context.current().with(parentSpan)).startSpan();
    this.password = password;
    span.end();
  }

  public String getEmail() {
    Span span = tracer.spanBuilder("Domain: getEmail").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return email;
  }

  public void setEmail(String email) {
    Span span = tracer.spanBuilder("Domain: setEmail").setParent(Context.current().with(parentSpan)).startSpan();
    this.email = email;
    span.end();
  }

  public String getFirstName() {
    Span span = tracer.spanBuilder("Domain: getFirstName").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return firstName;
  }

  @Validate(required = true, on = { "newAccount", "editAccount" })
  public void setFirstName(String firstName) {
    Span span = tracer.spanBuilder("Domain: setFirstName").setParent(Context.current().with(parentSpan)).startSpan();
    this.firstName = firstName;
    span.end();
  }

  public String getLastName() {
    Span span = tracer.spanBuilder("Domain: getLastName").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return lastName;
  }

  @Validate(required = true, on = { "newAccount", "editAccount" })
  public void setLastName(String lastName) {
    Span span = tracer.spanBuilder("Domain: setLastName").setParent(Context.current().with(parentSpan)).startSpan();
    this.lastName = lastName;
    span.end();
  }

  public String getStatus() {
    Span span = tracer.spanBuilder("Domain: getStatus").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return status;
  }

  public void setStatus(String status) {
    Span span = tracer.spanBuilder("Domain: setStatus").setParent(Context.current().with(parentSpan)).startSpan();
    this.status = status;
    span.end();
  }

  public String getAddress1() {
    Span span = tracer.spanBuilder("Domain: getAddress1").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return address1;
  }

  public void setAddress1(String address1) {
    Span span = tracer.spanBuilder("Domain: setAddress1").setParent(Context.current().with(parentSpan)).startSpan();
    this.address1 = address1;
    span.end();
  }

  public String getAddress2() {
    Span span = tracer.spanBuilder("Domain: getAddress2").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return address2;
  }

  public void setAddress2(String address2) {
    Span span = tracer.spanBuilder("Domain: setAddress2").setParent(Context.current().with(parentSpan)).startSpan();
    this.address2 = address2;
    span.end();
  }

  public String getCity() {
    Span span = tracer.spanBuilder("Domain: getCity").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return city;
  }

  public void setCity(String city) {
    Span span = tracer.spanBuilder("Domain: setCity").setParent(Context.current().with(parentSpan)).startSpan();
    this.city = city;
    span.end();
  }

  public String getState() {
    Span span = tracer.spanBuilder("Domain: getState").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return state;
  }

  public void setState(String state) {
    Span span = tracer.spanBuilder("Domain: setState").setParent(Context.current().with(parentSpan)).startSpan();
    this.state = state;
    span.end();
  }

  public String getZip() {
    Span span = tracer.spanBuilder("Domain: getZip").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return zip;
  }

  public void setZip(String zip) {
    Span span = tracer.spanBuilder("Domain: setZip").setParent(Context.current().with(parentSpan)).startSpan();
    this.zip = zip;
    span.end();
  }

  public String getCountry() {
    Span span = tracer.spanBuilder("Domain: getCountry").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return country;
  }

  public void setCountry(String country) {
    Span span = tracer.spanBuilder("Domain: setCountry").setParent(Context.current().with(parentSpan)).startSpan();
    this.country = country;
    span.end();
  }

  public String getPhone() {
    Span span = tracer.spanBuilder("Domain: getPhone").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return phone;
  }

  public void setPhone(String phone) {
    Span span = tracer.spanBuilder("Domain: setPhone").setParent(Context.current().with(parentSpan)).startSpan();
    this.phone = phone;
    span.end();
  }

  public String getFavouriteCategoryId() {
    Span span = tracer.spanBuilder("Domain: getFavouriteCategoryId").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return favouriteCategoryId;
  }

  public void setFavouriteCategoryId(String favouriteCategoryId) {
    Span span = tracer.spanBuilder("Domain: setFavouriteCategoryId").setParent(Context.current().with(parentSpan)).startSpan();
    this.favouriteCategoryId = favouriteCategoryId;
    span.end();
  }

  public String getLanguagePreference() {
    Span span = tracer.spanBuilder("Domain: getLanguagePreference").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return languagePreference;
  }

  public void setLanguagePreference(String languagePreference) {
    Span span = tracer.spanBuilder("Domain: setLanguagePreference").setParent(Context.current().with(parentSpan)).startSpan();
    this.languagePreference = languagePreference;
    span.end();
  }

  public boolean isListOption() {
    Span span = tracer.spanBuilder("Domain: isListOption").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return listOption;
  }

  public void setListOption(boolean listOption) {
    Span span = tracer.spanBuilder("Domain: setListOption").setParent(Context.current().with(parentSpan)).startSpan();
    this.listOption = listOption;
    span.end();
  }

  public boolean isBannerOption() {
    Span span = tracer.spanBuilder("Domain: isBannerOption").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return bannerOption;
  }

  public void setBannerOption(boolean bannerOption) {
    Span span = tracer.spanBuilder("Domain: setBannerOption").setParent(Context.current().with(parentSpan)).startSpan();
    this.bannerOption = bannerOption;
    span.end();
  }

  public String getBannerName() {
    Span span = tracer.spanBuilder("Domain: getBannerName").setParent(Context.current().with(parentSpan)).startSpan();
    span.end();
    return bannerName;
  }

  public void setBannerName(String bannerName) {
    Span span = tracer.spanBuilder("Domain: setBannerName").setParent(Context.current().with(parentSpan)).startSpan();
    this.bannerName = bannerName;
    span.end();
  }

}
