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
package org.mybatis.jpetstore.tracing.annotation;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TelemetryConfig {

  // KeyValue[] attributes() default {};

  String[] attributes() default {};

  String kind() default "";

  boolean recordStatus() default false;

  boolean recordException() default false;

  String ContextPropagationUrl() default "";

  int incrementBy() default 0;

  // 添加能夠獲得變數值的功能,限於Field中
  String[] varNames() default {};

  @Target({})
  @Retention(RetentionPolicy.RUNTIME)
  @interface KeyValue {
    String key() default "";

    String value() default "";
  }
}
