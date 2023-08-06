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
package org.mybatis.jpetstore.tracing;

import com.sun.management.*;
import java.lang.management.ManagementFactory;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;

import java.util.concurrent.TimeUnit;

public class Tracing {
  private OpenTelemetry openTelemetry;
  private SdkMeterProvider meterProvider;
  private static Meter meter;

  private Tracing() {

    /*
     * Metrics
     */
    meterProvider = SdkMeterProvider.builder()
        .registerView(InstrumentSelector.builder().setName("Num_Of_Actionbean").build(),
            View.builder().setName("Num_Of_Actionbean").build())
        .registerMetricReader(PeriodicMetricReader
            .builder(OtlpGrpcMetricExporter.builder().setEndpoint("http://localhost:4317").build()).build())
        .build();

    /* opentelemetry instance init */
    openTelemetry = OpenTelemetrySdk.builder().setMeterProvider(meterProvider).setPropagators(propagators).buildAndRegisterGlobal();

    /*
     * get from OpenTelemetry instance
     */
    meter = openTelemetry.getMeter("jpetstore-automatic");

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
}
