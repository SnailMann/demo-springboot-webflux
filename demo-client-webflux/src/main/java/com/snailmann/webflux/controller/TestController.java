package com.snailmann.webflux.controller;

import com.snailmann.webflux.api.UserApi;
import com.snailmann.webflux.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class TestController {

    @Autowired
    UserApi userApi;

    @GetMapping("/")
    public void test(){
        Flux<User> user = userApi.getAllUser();
        user.subscribe(System.out::println);
    }

}
