## Intro
This is a project implementation for researching OpenTelemetry using AOP mechanism  
provide a reference to use OpenTelemetry with AOP, aiming to improve the maintenance of code while having the ability to customize details.  
based on : JPetStore (https://github.com/mybatis/jpetstore-6)

## How to Start
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

## AOP usage
#### general
OpenTelemetry has traces, metrics and logs three signals, gauge of metrics and logs will not use AOP to fulfill the usage  
since gauge should be set in OpenTelemetry SDK and logs should be used as usual but added to span through OpenTelemetry bridge.  
So only traces need to use AOP.
First of all, to enable a method to be a span, just add **@EnableTelemetry** on the class, this will enable whole method in the class to be a span.  
if you just want to make a specific method to be a span, then add on the method.
Second, use **@TelemetryConfig** on the class or method to configure the OpenTelemetry API
for example
```java
@TelemetryConfig(kind = "Client", recordStatus = true, recordException = true, incrementBy = 1)
```
#### span attributes
however, for some APIs, this simple approach cannot perfectly support, like attributes and event .etc  
so we have other approach to do it.
for attributes, do
```java
@TelemetryConfig(attributes = {"attrA", "attrB"})
public void foo() {
    int a = 1;
    int b = 2;
    // addSpanAttr=("attrA", a)
    // addSpanAttr=("attrB", b)
}
```
besides of @TelemetryConfig, you should also add the comments.  
because of we want attributes can get local variable, so it need to be commented inside the method.  
It use replacer to turn it into method call, after compiling, it will be turn back to comments.

#### span event
span event is valued by the event happened time, so it also needs to be commented inside the method.
```java
@TelemetryConfig
public void foo() {
    ...
    // addSpanEvent="eventA"
    ...
}
```
this will make the span have the event in the correct position, also done through the replacer.  
context prpagation have similar approach with this. Although we do not implement it, if one understand our approach, one can easily make it work.  

## Notice
* all of the approach is designed to improve the maintenance of commented
* you should import org.mybatis.jpetstore.tracing.annotation.\* and org.mybatis.jpetstore.util.\* to use the annotation and the comments.
* all the method in org.mybatis.jpetstore.util shouldn't and no need to be call maually, it may be rewritten by the replacer.
* span link haven't be implement, we are trying to do this.
* we use spring aop in the project, that is, only spring bean can be weaving. So we do little revise on the original Jpetstore to make domain modules be spring bean.
* use AspectJ can avoid much problem and some unreasonable code, but spring aop is much convinient if your whole project is compose of Spring.
