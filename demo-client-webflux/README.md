# custom framework

我们这里有三个资源包，把框架代码抽取到snailmann路径下

- src/main/java       
- src/main/resources
- src/main/snailmann   //框架代码

### 框架流程

1. 启动demo-client-webflux工程，ClientWebfluxApplication启动，向Spring容器中注入 jdkProxyCreator 以及 userApi
2. 首先由jdkProxyCreator，然后通过jdkProxyCreator代理类生成器为userApi接口动态生成实现类
3. 在启动项目的时候，代理类生成器会初始化服务器信息serverInfo。在具体的方法调用的时候，代理类生成器才根据注解ApiServer和方法生成MethodInfo信息
4. 然后动态生成的实现类最后用已知的serverInfo信息和methodInfo信息去传递给rest调用类，实现http调用
5. WebClientRestHandler只是一个用于请求的类，最后返回流