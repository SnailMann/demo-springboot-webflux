package com.snailmann.webflux.controller;

import com.snailmann.webflux.entity.User;
import com.snailmann.webflux.repository.UserRepository;
import com.snailmann.webflux.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 这里不使用传统的@Autowired方式，使用推荐的构造注入方式
     * 惊讶脸是居然也不需要用注解
     */
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
        return userRepository.findAll();
    }

    /**
     * 多个元素流式返回,以SSE形式返回
     *
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userRepository.findAll();
    }


    /**
     * 插入数据
     *
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> createUser(@RequestBody @Valid User user) {
        //spring data jpa里面，新增和修改都是save，有id是修改，id为空时新增
        //如果Id是你自定义的，会抛异常。这个跟User对象中@Id注解有关

        //检查名字是否含有违规名称
        CheckUtil.checkName(user.getName());

        return userRepository.save(user);
    }


    /**
     * 根据id来删除用户
     * 存在时返回200，不存在返回404
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable String id) {
        //不能使用deleteById,因为这个方式没有返回值，不能判断数据是否存在，目前不符合我们的需求
        //首先我们根据id找是否存在该数据
        return userRepository.findById(id)
                //当你要操作数据，并返回一个Mono时，应该使用flatMap
                //当你不操作数据时，只是转换数据，把A变成B,则使用map
                //其实就是flatMap返回一个Mono，还可以接着处理,而Map返回的是完成处理的数据,有点类似JDK8中flatMap返回数据的stream。
                //与JDK8对应的Stream更类似的应该是Flux的flatMap
                //如果存在则删除
                .flatMap(user -> userRepository.delete(user)
                        //删除后并返回200状态码
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                //如果不存在则返回404
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * 对User进行修改
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable String id, @Valid @RequestBody User user) {

        //首先找数据库是否存在该数据
        return userRepository.findById(id)
                //如果存在，则通过flatMap对aUser进行操作
                //则修改数据，有id才算修改
                .flatMap(aUser -> {
                    aUser.setAge(user.getAge());
                    aUser.setName(user.getName());
                    return userRepository.save(aUser);
                })
                //修改完成之后，将数据封装成ResponseEntity返回
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                //如果不存在则404
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        //与下面代码同意
        /*return userRepository.findById(id)
                .map(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    userRepository.save(u);
                    return new ResponseEntity<>(u, HttpStatus.OK);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
    }

    /**
     * 根据Id查找用户
     * 存在则返回用户，不存在则返回404
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable String id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄段来查找用户集合
     * 因为是多个元素，所以返回Flux
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> getByAge(@PathVariable int start, @PathVariable int end) {
        return userRepository.getByAgeBetween(start, end);
    }


    /**
     * SSE流式
     * 根据年龄段来查找用户集合
     * 因为是多个元素，所以返回Flux
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> StreamGetByAge(@PathVariable int start, @PathVariable int end) {
        return userRepository.getByAgeBetween(start, end);
    }

    /**
     * 返回1到40岁的用户
     *
     * @return
     */
    @GetMapping("/age/1/40")
    public Flux<User> getBy1And40() {
        return userRepository.getOldUser();
    }

}
