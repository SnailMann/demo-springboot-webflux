# webflux

- JDK 11

### Reactive Stack VS Servlet Stack

| Reactive Stack | Servlet Stack | 
| :------: | :------: | 
| Netty,Servlet 3.1+ Containers | Servlet Containers |
| Reactive Streams Adapters | Servlet API |
| Spring Security Reactive | Spring Security |
| Spring WebFlux  | Spring MVC |
| Spring Data Reactive Repository | Spring Data Repository |

1. Spring Webflux是一种非阻塞的开发模式，而我们传统的SpringMVC是一种阻塞式的开发模式
2. 目前的情况是，基于JDBC连接的关系型数据库还不支持响应式开发，所以还需要等待生态成熟
3. Webflux的先天优势就是支持高并发，默认使用netty作为服务器，而不是servlet


### 开发模式

| before | after | 
| :------: | :------: | 
| @Controller,@RequestMapping | Router Functions |
| Spring MVC | Spring-webflux |
| Servlet API | HTTP/Reactive Streams |
| Servlet Container  | Tomcat,Jetty,Netty,Undertow |

1. Router Function是一种类似函数式编程的风格，更加的简洁和灵活


### 异步Servlet

1. 为什么要使用异步Servlet?同步Servlet阻塞了什么？

    - 同步Servelt就是一个request与一个response的流程，一个request发出，在response没有返回时，Servlet会被业务代码一直阻塞，知道response返回
    - Servlet 3.1+后支持异步Servlet,异步Servlet的作用就是不阻塞Tomcat线程，有点像nio机制，把耗时操作交给子线程执行


2. 异步Servlet是怎么样工作的？


### 传统@Controller,@RequestMapping VS Router Functions

| before | after | 
| :------: | :------: | 
| ServerRequest | Router Functions |

HandlerFunction(输入ServerRequest返回ServerResponse)
-> 
RouterFunction(请求URL和HandlerFunction对应起来)
->
HttpHandler
->
Server处理

