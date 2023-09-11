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
package org.mybatis.jpetstore.util;

import io.opentelemetry.api.trace.Span;

import java.util.HashMap;

public class ThreadLocalContext {

  private static ThreadLocal<Span> parentSpan = new ThreadLocal<>();
  private static ThreadLocal<HashMap<String, Object>> attributes = ThreadLocal.withInitial(HashMap::new);

  public static Span getParentSpan() {
    return parentSpan.get();
  }

  public static void setParentSpan(Span span) {
    parentSpan.set(span);
  }

  public static void clearParentSpan() {
    parentSpan.remove();
  }

  public static Object getAttributes(String key) {
    HashMap<String, Object> attributesMap = attributes.get();
    return attributesMap.get(key);
  }

  public static void __putAttributes(String key, Object value) {
    HashMap<String, Object> attributesMap = attributes.get();
    attributesMap.put(key, value);
  }

  public static void clearAttributes() {
    attributes.get().clear();
    ;
  }

}
