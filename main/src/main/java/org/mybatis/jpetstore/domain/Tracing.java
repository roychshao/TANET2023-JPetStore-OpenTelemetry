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

import java.util.concurrent.TimeUnit;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
// Jaeger
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;

import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

public class Tracing {
  public Resource resource;
  public SdkTracerProvider sdkTracerProvider;
  public OpenTelemetry openTelemetry;
  private Tracer tracer;
  private JaegerGrpcSpanExporter jaegerExporter;

  public Tracing() {

    // Jaeger
    ManagedChannel jaegerChannel = ManagedChannelBuilder.forAddress("localhost", 3336)
        .usePlaintext()
        .build();
    
    jaegerExporter = JaegerGrpcSpanExporter.builder()
        .setEndpoint("localhost:3336")
        .setTimeout(30, TimeUnit.SECONDS)
        .build();

    // Create a default resource with a service name attribute
    Resource defaultResource = Resource.getDefault();
    Resource serviceNameResource = Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "jpetstore-main"));
    this.resource = defaultResource.merge(serviceNameResource);

    // Create a tracer provider with a batch span processor and the given resource
    // BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(OtlpGrpcSpanExporter.builder().build()).build();
    BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(jaegerExporter).build();
    this.sdkTracerProvider = SdkTracerProvider.builder().addSpanProcessor(spanProcessor).setResource(resource).build();

    // Create an OpenTelemetry instance with the given tracer provider and propagator
    W3CTraceContextPropagator propagator = W3CTraceContextPropagator.getInstance();
    ContextPropagators propagators = ContextPropagators.create(propagator);

    this.openTelemetry = OpenTelemetrySdk.builder().setTracerProvider(sdkTracerProvider).setPropagators(propagators)
        .buildAndRegisterGlobal();

    this.tracer = this.openTelemetry.getTracer("jpetstore-main", "1.0.0");
    System.out.println(tracer);
  }

  public Tracer getTracer() {
    return this.tracer;
  }
}
