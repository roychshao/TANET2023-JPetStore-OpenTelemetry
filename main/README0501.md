# SpanMapping和Error處理
---
### SpanMapping
> 原因: 在多個連線的時候thread可能會亂掉
##### 解決:
**使用sessionId作為key, Span作為value  
使用相同sessionId的span作為parentSpan**

> 問題: 目的是不要使用thread去存資料,但這種作法還是會需要傳遞sessionId  

##### 解決:
**使用Opentelemetry的Context,將sessionId存在Context中  
之後再透過Context拿出sessionId後map出parentSpan**

> 延伸: 那是不是直接使用Context來傳遞Span即可？
於是... 

// TracingInterceptor.java
```java
@Intercepts(LifecycleStage.EventHandling)
public class TracingInterceptor implements Interceptor {
  private transient final Tracer tracer = Tracing.getTracer();
  private static final ContextKey<Span> PARENTSPAN_KEY = ContextKey.named("parentSpan-key");

  public void init() {
  }

  public void destroy() {
  }

  public Resolution intercept(ExecutionContext context) throws Exception {
    Resolution resolution = null;
    ActionBean actionBean = context.getActionBean();
    if (actionBean == null) {
      System.out.println("Interceptor: actionBean is null");
      return null;
    } else {
      String actionBeanClassName = actionBean.getClass().getName();
      String actionBeanMethodName = context.getHandler().getName();

      // 執行ActionBean方法前的處理邏輯
      Span span = tracer.spanBuilder(actionBeanClassName + ": " + actionBeanMethodName).startSpan();
      Context newContext = Context.current().with(PARENTSPAN_KEY, span);
      newContext.makeCurrent();

      try (Scope ss = span.makeCurrent()) {
        // 執行ActionBean方法
        resolution = context.proceed();
        span.setStatus(StatusCode.OK);
      } catch (Throwable t) {
        span.setStatus(StatusCode.ERROR, t.getMessage());
        span.recordException(t);
      } finally {
        // 執行ActionBean方法後的處理邏輯
        span.end();
      }
      return resolution;
    }
  }

  public static ContextKey getParentSpanKey() {
    return PARENTSPAN_KEY;
  }
}
```

// TracingAspect.java
```java
private static final ContextKey<Span> PARENTSPAN_KEY = TracingInterceptor.getParentSpanKey();

Span span = Context.current().get(PARENTSPAN_KEY);
```
***注意: Opentelemetry的ContextKey必須要是同一個,而不是name一樣即可***
<br/>

### Error處理
之前try-catch無法抓到
> 原因: Interceptor的LifeCycle是在綁定和驗證actionbean的時候攔截,比Exception被Throw的時間早

##### 解決:
**將LifeCycle改成EventHandling,是更大的範圍,即可成功捕獲Exception**  
並且發現不加throws Exception,不加try-catch 只要有Exception都可以成功catch到
```java
@Intercepts(LifecycleStage.EventHandling)
```

### 還原使用者旅程
**在每個span中將sessionId添加進去即可在jaeger中透過尋找tag的方式得到某特定session的旅程**  

> 但sessionId對於辨識使用者沒有任何作用

**在span中添加使用者資訊: 例如使用者名稱...**  

> TracingInterceptor中獲取當時Account.java中的username變數  
如果非空值就寫入span中

問題: 目前沒有找到方法能夠獲得服務中唯一的Account實例,除非將有關的變數或方法設為static  
=> 要改動原本程式碼
