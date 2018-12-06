# custom framework

我们这里有三个资源包，把框架代码抽取到snailmann路径下

- src/main/java       
- src/main/resources
- src/main/snailmann   //框架代码

### 框架流程

1. 启动demo-client-webflux工程，ClientWebfluxApplication启动，向Spring容器中注入 jdkProxyCreator 以及 userApi
2. 首先由jdkProxyCreator，然后通过jdkProxyCreator代理类生成器为userApi接口生成