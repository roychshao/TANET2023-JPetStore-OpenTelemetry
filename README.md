# TANET2023-JPetStore-OpenTelemetry(AOP)

#### Intro
This is a project implementation for researching OpenTelemetry using AOP mechanism  
The research objective is JPetStore form https://github.com/mybatis/jpetstore-6

#### How to Start
For running this project, there are some pre-works needed.  
* First, running Jaeger all-in-one, this is the observability backend for the traces.
```
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 4318:4318 \
  -p 14250:14250 \
  -p 14268:14268 \
  -p 14269:14269 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.48
```
we muted 4317 port since it has a conflict with OpenTelemetry Collector, which use 4317 port as well. 


* For the observability backend for metrics and logs, we use OpenTelemetry Collector(otelcol)  

download form https://opentelemetry.io/docs/collector/getting-started/, and use config.yaml to startup

* Last, we can run the project by
```
cd main \
./exec.sh
```
in exec.sh is the startup command of JPetStore

* now visit localhost:8080/jpetstore and localhost:16686

you can see the traces in Jaeger UI while metrcis and logs in otelcol console

#### AOP usage
in the main/src/main/java/org/mybatis/jpetstore/tracing/annotation, you can define the annotation of your self and implement it with advices in main/src/main/java/org/mybatis/jpetstore/tracing/.  
then use annotation for the instrumentation
