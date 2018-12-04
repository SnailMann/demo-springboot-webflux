package com.snailmann.webflux.controller;

import com.snailmann.webflux.entity.User;
import com.snailmann.webflux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 多个数据一次性返回
     *
     * @return
     */
    @GetMapping("/all")
    public Flux<User> getAll() {
        User user = new User();
        user.setId("1");
        user.setName("jerry");
        user.setAge(1);
        userRepository.insert(user);
        return userRepository.findAll();
    }

    /**
     * 多个元素流式返回
     *
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }


}
