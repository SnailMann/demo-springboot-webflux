package com.snailmann.webflux.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class TestController {

    /**
     * 旧接口old test end肯定在5s后才输出
     * @return
     */
    @GetMapping("/old/test")
    private String oldTest()   {
        log.info("old test begin");
        String result = doSomething();
        log.info("old test end");
        return "result";
    }

    /**
     * 新接口new test end几乎与begin同时输出，就类似传统中的异步接口
     * @return
     */
    @GetMapping("/new/test")
    private Mono<String> newTest()   {
        log.info("new test begin");
        Mono<String> result = Mono.fromSupplier(this::doSomething);
        log.info("new test end");
        return result;
    }

    private String doSomething()  {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "do something";

    }
    public static void main(String[] args) {
        String[] strs = {"1","2","3"};
        Mono<List<Integer> >list = Flux.fromArray(strs).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> integers = list.block();
        System.out.println(integers);


    }
}
