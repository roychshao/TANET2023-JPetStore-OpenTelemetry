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

import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.autoconfigure.AutoConfiguredOpenTelemetrySdk;

public class Tracing {
  public static transient OpenTelemetrySdk opentelemetry = AutoConfiguredOpenTelemetrySdk.initialize()
      .getOpenTelemetrySdk();
  private static Tracer tracer = opentelemetry.getTracer("jpetstore-autoconfig", "1.0.0");

  public Tracing() {
    // OpenTelemetrySdk opentelemetry = AutoConfiguredOpenTelemetrySdk.initialize().getOpenTelemetrySdk();

    // AutoConfiguredOpenTelemetrySdk.builder()
    // .addTracerProviderCustomizer(
    // (sdkTracerProviderBuilder, configProperties) ->
    // sdkTracerProviderBuilder.addSpanProcessor(
    // new SpanProcessor() { /* implementation omitted for brevity */ }))
    // .build();
  }

  public static synchronized Tracer getTracer() {
    return tracer;
  }
}
