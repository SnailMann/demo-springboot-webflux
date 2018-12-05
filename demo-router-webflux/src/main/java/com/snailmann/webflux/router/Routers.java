package com.snailmann.webflux.router;


import com.snailmann.webflux.Handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 现在我们来开发RouterFunction
 * 这有点类似SpringMVC中的DispatcherServlet
 */
@Configuration
public class Routers {


    /**
     * 具体的路由方法，将handler在参数中注入，也可以传统的方式@Autowired
     *
     * @param handler
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler){
        return nest(
                // 相当于类上面的 @RequestMapping("/user")
                path("/user"),
                // 下面的相当于方法上面的 @RequestMapping
                // 得到所有用户
                route(  GET("/"), handler::getAllUser)
                        // 创建用户
                        .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON_UTF8)),
                                handler::createUser)
                        // 删除用户
                        .andRoute(DELETE("/{id}"),handler::deleteUserById));
    }
}
