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
import io.opentelemetry.context.Scope;

import java.util.Optional;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.Tracing;
import org.mybatis.jpetstore.mapper.AccountMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AccountService.
 *
 * @author Eduardo Macarron
 */
@Service
public class AccountService {

  private final AccountMapper accountMapper;
  private final Tracing tracing = new Tracing();

  public AccountService(AccountMapper accountMapper) {
    this.accountMapper = accountMapper;
  }

  public Account getAccount(String username) {
    return accountMapper.getAccountByUsername(username);
  }

  public Account getAccount(String username, String password) {
    return accountMapper.getAccountByUsernameAndPassword(username, password);
  }

  /**
   * Insert account.
   *
   * @param account
   *          the account
   */
  @Transactional
  public void insertAccount(Account account) {
    Tracer tracer = tracing.getTracer();
    Span span = tracer.spanBuilder("service: insertAccount").startSpan();
    try (Scope ss = span.makeCurrent()) {
      accountMapper.insertAccount(account);
      accountMapper.insertProfile(account);
      accountMapper.insertSignon(account);
    } finally {
      span.end();
    }
  }

  /**
   * Update account.
   *
   * @param account
   *          the account
   */
  @Transactional
  public void updateAccount(Account account) {
    Tracer tracer = tracing.getTracer();
    Span span = tracer.spanBuilder("service: updateAccount").startSpan();
    try (Scope ss = span.makeCurrent()) {
      accountMapper.updateAccount(account);
      accountMapper.updateProfile(account);

      Optional.ofNullable(account.getPassword()).filter(password -> password.length() > 0)
          .ifPresent(password -> accountMapper.updateSignon(account));
    } finally {
      span.end();
    }
  }

}
