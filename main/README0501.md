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

<br/>

### Error處理
之前try-catch無法抓到
> 原因: Interceptor的LifeCycle是在綁定和驗證actionbean的時候攔截,比Exception被Throw的時間早

##### 解決:
**將LifeCycle改成EventHandling,是更大的範圍,即可成功捕獲Exception**  
並且發現不加throws Exception,不加try-catch 只要有Exception都可以成功catch到
