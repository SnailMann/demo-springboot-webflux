package com.snailmann.webflux.Handler;

import com.snailmann.webflux.entity.User;
import com.snailmann.webflux.repository.UserRepository;
import com.snailmann.webflux.util.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


/**
 * 以前模式的Controller,现在就是HandlerFunction
 * 我们可以知道，我们的方法返回的都是Flux或者是Mono,这都是流，都是发布者。
 * 我们不要发布者.subcribe自行消费。而是都交给Spring框架去消费
 */
@Component
@Slf4j
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 获得所有用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userRepository.findAll(), User.class);
    }

    /**
     * 根据id来获取User
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getById(ServerRequest request){
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userRepository.findById(id),User.class);
    }

    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        log.info("Createing User now");
        //userRepository.save方法不支持Mono或Flux参数，所以我们要用saveAll，需要传入发布者publisher
        //而Mono和Flux就是发布者实现了Publisher
        Mono<User> user = request.bodyToMono(User.class);

        return user.flatMap(u -> {
            //这里要捕获异常，但是为了让实现更加的优雅，所以我们这里使用aop的进行捕获
            //如果出现异常交给ExceptionHandler去处理，如果没有异常，则flatMap返回Mono<ServerResponse>
            CheckUtil.checkName(u.getName());
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    //如果这里使用userRepository.savaAll(user)会抛异常：Only one connection receive subscriber allowed.
                    .body(userRepository.save(u), User.class);
        });

    }

    /**
     * 根据Id来删除用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUserById(ServerRequest request){
        String id = request.pathVariable("id");
        return userRepository.findById(id)
                .flatMap(user -> userRepository.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}
