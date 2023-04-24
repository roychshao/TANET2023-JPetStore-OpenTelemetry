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

import java.util.HashMap;
import java.util.Map;

public class SpanMapping {
  private static final Map<String, Span> spanMapper = new HashMap<>();

  public static Span get(String sessionId) {
    return spanMapper.get(sessionId);
  }

  public static void set(String sessionId, Span span) {
    spanMapper.put(sessionId, span);
  }

  public static void remove(String sessionId) {
    spanMapper.remove(sessionId);
  }

  public static void clear() {
    spanMapper.clear();
  }
}
