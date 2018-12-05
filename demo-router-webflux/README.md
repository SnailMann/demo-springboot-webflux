### 传统@Controller,@RequestMapping VS Router Functions

HandlerFunction(输入ServerRequest返回ServerResponse)

-> 
RouterFunction(请求URL和HandlerFunction对应起来)

->
HttpHandler(将RouterFunction包装成HttpHandler)

->
Server处理(netty或者Servlet3.1+容器)

### webflux依赖 

webflux默认使用netty作为服务器

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        
从spring-boot-starter-webflux包下，我们就可以看到netty包的身影，如果要用Tomcat作为应用服务器
我们可以



```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-netty</artifactId>
        </exclusion>
    </exclusions>
</dependency>
 
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
</dependency>

```

切记纯webflux项目中，不要导入传统spring-boot-starter-web包

```xml

    <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

```
    
        
传统的spring-boot-starter-web是嵌入式tomcat的，
它会覆盖webflux以netty作为默认应用服务器的配置，如果真的要使用，可以exclusion嵌入式tomcat包

### RouterFunction方式的参数校验

我们知道没有参数校验的实现，在实际生产环境中是不实用的