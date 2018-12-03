# Servlet

### Tomcat启动

- 需要外部Tomcat容器来启动

### 异步Servlet

1. 为什么要使用异步Servlet?同步Servlet阻塞了什么？

    - 同步Servelt就是一个request与一个response的流程，一个request发出，在response没有返回时，Servlet会被业务代码一直阻塞，知道response返回
    - Servlet 3.1+后支持异步Servlet,异步Servlet的作用就是不阻塞Tomcat线程，有点像nio机制，把耗时操作交给子线程执行


2. 异步Servlet是怎么样工作的？