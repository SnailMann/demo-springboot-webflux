package com.snailmann.webflux.controller;

import com.snailmann.webflux.api.UserApi;
import com.snailmann.webflux.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

    @Autowired
    UserApi userApi;


    @GetMapping("/test")
    public void test(){
        //测试信息提取，不订阅，不会发出实际请求，但会进入我们的代理类
        userApi.getAllUser();
        userApi.getUserById("11111");
        userApi.deleteUserById("222222");
        userApi.createUser(User.builder().name("snailmann").age(34).build());
    }

    @GetMapping("/")
    public void getAll() {
        Flux<User> user = userApi.getAllUser();
        user.subscribe(System.out::println);


    }

    @GetMapping("/{id}")
    public void get(@PathVariable String id){
        Mono<User> user =  userApi.getUserById(id);
        user.subscribe(System.out::println);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        Mono<Void> response = userApi.deleteUserById(id);
       response.subscribe(aVoid -> System.out.println(response));
    }

    @PostMapping("/")
    public void create(@RequestBody User user){
        Mono<User> response = userApi.createUser(user);
        System.out.println(response);
        response.subscribe(System.out::println);

    }


}
