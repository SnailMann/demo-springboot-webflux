package com.snailmann.webflux.api;

import com.snailmann.framework.annotation.ApiServer;
import com.snailmann.webflux.entity.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ApiServer("http://localhost:8080/user")
public interface UserApi {

    @GetMapping("/")
    Flux<User> getAllUser();

    @PostMapping("/")
    Mono<User> createUser(@RequestBody User user);

    @PutMapping("/{id}")
    Mono<User> updateUser(@PathVariable String id,@RequestBody User user);

    @GetMapping("/{id}")
    Mono<User> getUserById(@PathVariable String id);

    @DeleteMapping("/{id}")
    Mono<Void> deleteUserById(@PathVariable String id);
}
