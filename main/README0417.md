# OpenTelemetry on Jpetstore
---
### 建立Tracing.java來管理OTL
```java
public class Tracing {
  private Resource resource;
  private SdkTracerProvider sdkTracerProvider;
  private OpenTelemetry openTelemetry;
  private static Tracer tracer;
  private JaegerGrpcSpanExporter jaegerExporter;
  private static Tracing instance;

  private Tracing() {
    // Jaeger
    ManagedChannel jaegerChannel = ManagedChannelBuilder.forAddress("localhost", 14250).usePlaintext().build();

    jaegerExporter = JaegerGrpcSpanExporter.builder().setEndpoint("http://localhost:14250")
        .setTimeout(30, TimeUnit.SECONDS).build();

    // Create a default resource with a service name attribute
    Resource defaultResource = Resource.getDefault();
    Resource serviceNameResource = Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "jpetstore-main"));
    resource = defaultResource.merge(serviceNameResource);

    // Create a tracer provider with a batch span processor and the given resource
    BatchSpanProcessor spanProcessor = BatchSpanProcessor.builder(jaegerExporter).build();
    sdkTracerProvider = SdkTracerProvider.builder().addSpanProcessor(spanProcessor).setResource(resource).build();

    // Create an OpenTelemetry instance with the given tracer provider and propagator
    W3CTraceContextPropagator propagator = W3CTraceContextPropagator.getInstance();
    ContextPropagators propagators = ContextPropagators.create(propagator);

    openTelemetry = OpenTelemetrySdk.builder().setTracerProvider(sdkTracerProvider).setPropagators(propagators)
        .buildAndRegisterGlobal();

    tracer = openTelemetry.getTracer("jpetstore-main", "1.0.0");
  }

  public static synchronized Tracer getTracer() {
    if (instance == null) {
      instance = new Tracing();
    }
    return instance.tracer;
  }
}
```
<ul>
<li>設定Jaeger: 14250port, 14250port接受model.proto資料類型作為gprc的接口</li>
<li>設定Resource</li>
<li>設定spanProcessor並將jaegerExporter放入</li>
<li>W3CTraceContextPropagatorg使用http做Context Propagation,這裡沒有使用到</li>
<li>設定openTelemetry並註冊在global上(其中有個GlobalOpentelemetry.set()只能被呼叫一次)</li>
<li>使用SingleTon方法來獲得tracer, 但由於使用synchronized造成有可能在操作的時候返回的tracer為null使得網頁error 500</li>

### Trace設計

***jsp呼叫哪層的function就從哪裡開始***
> 大部分都是從ActionBean層開始, 但也有直接呼叫Domain層的

### Context Propagation

所有的函式全部由以下格式的程式碼包住
```java
public void foo() {
    Span span = tracer.spanBuilder("domain: foo").startSpan();
    try (Scope ss = span.makeCurrent()) {
        // do something
    } finally {
        span.end();
    }
    // return here
}
```
> **Scope: 被try包含住的區域中,span會被設定為當前活躍的span  
因此在這之中被呼叫的inner function會成為outer function的childSpan**

### Mapper層

缺乏技術力

### Auto

**作法: 先創建javaagent後在原本的code中使用@WithSpan選擇要trace的method**
> ***問題: 創建javaagent使用java -javaagent:path/to/opentelemetry-javaagent.jar -Dotel.service.name=your-service-name -jar myapp.jar來達成  
但jpetstore package出來的是war包,不支援***
