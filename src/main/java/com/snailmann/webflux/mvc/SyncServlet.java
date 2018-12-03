package com.snailmann.webflux.mvc;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟同步Servlet
 */
@RestController
@RequestMapping("/sync")
public class SyncServlet extends HttpServlet {

    AtomicInteger count = new AtomicInteger(0);


    @GetMapping("/servlet")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //模拟耗时操作
        doSomeThing(request,response);
    }

    private void doSomeThing(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("request:" + request.getClass()  + " and reponse" + response.getClass());
        Long startTime = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sequence: " + count.incrementAndGet() + " spend :" +(double) (System.currentTimeMillis() - startTime) / 1000 + "s");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
