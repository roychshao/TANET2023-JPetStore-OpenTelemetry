# ActionBean的AOP與tracer的Singleton問題
---
### actionbean問題
> **由於actionbean是由stripes管理,而不是spring, 因此在使用Aspectj時出現錯誤**

解決： 使用stripes的Interceptor為actionbean個別實做AOP  
<br/>
在web.xml的Stripes Filter中加入
```xml
<init-param>
    <param-name>Interceptor.Classes</param-name>
    <param-value>org.mybatis.jpetstore.domain.TracingInterceptor</param-value>
</init-param>
```

創建TracingInterceptor.java
```java
package org.mybatis.jpetstore.domain;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;

import javax.servlet.http.*;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.*;

@Intercepts(LifecycleStage.BindingAndValidation)
public class TracingInterceptor implements Interceptor {
  private transient final Tracer tracer = Tracing.getTracer();
  private static final ThreadLocal<Span> spanLocal = new ThreadLocal<>();

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
      spanLocal.set(span);
      try (Scope ss = span.makeCurrent()) {
        // 執行ActionBean方法
        resolution = context.proceed();
      } finally {
        // 執行ActionBean方法後的處理邏輯
        span.end();
      }
      return resolution;
    }
  }

  public static Span getCurrentSpan() {
    Span currentSpan = spanLocal.get();
    // 讓thread結束時自動把變數清除
    // 如果直接remove會導致其它service的aspect拿不到這個span
    // spanLocal.remove();
    return currentSpan;
  }
}
```

* @Intercepts(LifecycleStage.BindingAndValidation): 表示攔截器在actionbean綁定跟驗證階段執行
* init: 初始化攔截器時呼叫
* destroy: 攔截器結束時呼叫
* 因為Interceptor創建的Scope在Aspectj中拿不到(原因未知),因此透過傳遞span手動設置父子span  
將span設置到thread中,在AspectJ切面中再透過getCurrentSpan()將parentSpan取出
> 不手動將span從thread中移除,因為一個actionbean的span會被多個service的span繼承,無法確認最後使用的service  
因此等待thread結束後自動刪除span

<br/>

在TracingAspect.java中
```java
    Span span = TracingInterceptor.getCurrentSpan();

    if (span == null) {
      span = tracer
          .spanBuilder(joinPoint.getSignature().getDeclaringTypeName() + ": " + joinPoint.getSignature().getName())
          .startSpan();
    } else {
      span = tracer
          .spanBuilder(joinPoint.getSignature().getDeclaringTypeName() + ": " + joinPoint.getSignature().getName())
          .setParent(Context.current().with(span)).startSpan();
    }
```
<br/>

***發現Aspectj沒有辦法對domain做切面,因為domain不屬於spring也不屬於stripes管理***  
並且目前將domain加入由spring管理的操作：
* 在applicationContext.xml加入: 
```xml
<context:component-scan base-package="org.mybatis.jpetstore.domain" />
```
* 並在所有的domain類前加入@Component  

已經嘗試過但spring還是沒有管理domain

### tracer的Singleton問題

***把synchronized拿掉就沒有問題了***

### OTLP + Jaeger的Exception

Opentelemetry有支持在span上紀錄error:
```java
import io.opentelemetry.api.trace.StatusCode;

span.setStatus(StatusCode.ERROR, e.getMessage());
span.recordException(e);
```
即可在span上紀錄error  
並且在Jaeger上也會成功顯示
<br/>
但在切面上使用會失敗  
並且需要在每個function中都使用try-catch語句  
***原因: catch不到原程式碼throw出的error***
