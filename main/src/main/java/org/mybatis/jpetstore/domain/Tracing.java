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

import static io.opentelemetry.api.common.AttributeKey.stringKey;

import com.sun.management.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.instrumentation.log4j.appender.v2_17.OpenTelemetryAppender;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.metrics.InstrumentSelector;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.View;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class Tracing {
  private static Tracing instance;
  private JaegerGrpcSpanExporter jaegerExporter;
  private ManagedChannel jaegerChannel;
  private OpenTelemetry openTelemetry;
  private Resource resource;
  private SdkTracerProvider sdkTracerProvider;
  private SdkMeterProvider meterProvider;
  private SdkLoggerProvider loggerProvider;
  private static Tracer tracer;
  private static Meter meter;
  private LongCounter counter;
  private Attributes attributes;

  private Tracing() {

    /*
     * Jaeger
     */
    jaegerChannel = ManagedChannelBuilder.forAddress("localhost", 14250).usePlaintext().build();

    jaegerExporter = JaegerGrpcSpanExporter.builder().setEndpoint("http://localhost:14250")
        .setTimeout(30, TimeUnit.SECONDS).build();

    /*
     * Resource
     */
    // Create a default resource with a service name attribute
    Resource defaultResource = Resource.getDefault();
    Resource serviceNameResource = Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "jpetstore-main"));
    resource = defaultResource.merge(serviceNameResource);

    /*
     * Traces
     */
    // Create a tracer provider with a batch span processor and the given resource
    // BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(jaegerExporter).build();
    BatchSpanProcessor spanProcessor = BatchSpanProcessor
        .builder(OtlpGrpcSpanExporter.builder().setEndpoint("http://localhost:4317").build()).build();
    sdkTracerProvider = SdkTracerProvider.builder().addSpanProcessor(spanProcessor).setResource(resource).build();

    // Create an OpenTelemetry instance with the given tracer provider and propagator
    W3CTraceContextPropagator propagator = W3CTraceContextPropagator.getInstance();
    ContextPropagators propagators = ContextPropagators.create(propagator);

    /*
     * Metrics
     */
    meterProvider = SdkMeterProvider.builder()
        .registerView(InstrumentSelector.builder().setName("Num_Of_Actionbean").build(),
            View.builder().setName("Num_Of_Actionbean").build())
        .registerMetricReader(PeriodicMetricReader
            .builder(OtlpGrpcMetricExporter.builder().setEndpoint("http://localhost:4317").build()).build())
        .build();

    // It is recommended that the API user keep a reference to Attributes they will record against
    attributes = Attributes.of(stringKey("Key"), "Test");

    /*
     * Logs
     */
    loggerProvider = SdkLoggerProvider.builder().addLogRecordProcessor(BatchLogRecordProcessor
        .builder(OtlpGrpcLogRecordExporter.builder().setEndpoint("http://localhost:4317").build()).build()).build();

    /*
     * Build OpenTelemetry instance
     */
    openTelemetry = OpenTelemetrySdk.builder().setTracerProvider(sdkTracerProvider).setMeterProvider(meterProvider)
        .setLoggerProvider(loggerProvider).setPropagators(propagators).buildAndRegisterGlobal();

    // OpenTelemetry Appender
    OpenTelemetryAppender.install(openTelemetry);

    /*
     * get from OpenTelemetry instance
     */
    tracer = openTelemetry.getTracer("jpetstore-main", "1.0.0");
    meter = openTelemetry.getMeter("jpetstore-main");
    counter = meter.counterBuilder("counter_test").setDescription("counter_test").setUnit("1").build();

    // 獲取Memory使用情況
    meter.gaugeBuilder("jvm.memory.total").setDescription("Current Memory Usage.").setUnit("byte")
        .buildWithCallback(result -> result.record(Runtime.getRuntime().totalMemory(), Attributes.empty()));

    // 獲取CPU使用情況
    OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    meter.gaugeBuilder("jvm.total.cpu").setDescription("Current CPU time.").setUnit("ms")
        .buildWithCallback(measurement -> {
          measurement.record(osBean.getProcessCpuTime() / 1000000.0, Attributes.empty());
        });
  }

  public static Tracer getTracer() {
    if (instance == null) {
      instance = new Tracing();
    }
    return instance.tracer;
  }

  public static Meter getMeter() {
    if (instance == null) {
      instance = new Tracing();
    }
    return instance.meter;
  }

  public static Attributes getAttributes() {
    if (instance == null) {
      instance = new Tracing();
    }
    return instance.attributes;
  }

  public static LongCounter getCounter() {
    if (instance == null) {
      instance = new Tracing();
    }
    return instance.counter;
  }
}
