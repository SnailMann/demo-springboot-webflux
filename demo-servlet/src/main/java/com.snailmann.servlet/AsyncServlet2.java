package com.snailmann.servlet;


import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟异步Servlet2
 */
@WebServlet(urlPatterns = "/async/servlet/2", asyncSupported = true)
public class AsyncServlet2 extends HttpServlet {

    AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //开启异步
        final AsyncContext asyncContext;
        if (request.isAsyncSupported()) {
            asyncContext = request.startAsync();
        } else {
            asyncContext = null;
        }

        //这里没有通过completeFuture去实现，而是通过asyncContext.start方法去实现异步
        asyncContext.start(() -> doSomeThing(request, response));
        //告诉系统，异步任务已经完成
        asyncContext.complete();
        System.out.println("异步调用执行完毕");

    }

    private void doSomeThing(ServletRequest request, ServletResponse response) {
        System.out.println("request:" + request.getClass() + " and reponse" + response.getClass());
        Long startTime = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sequence: " + count.incrementAndGet() + " spend :" + (double) (System.currentTimeMillis() - startTime) / 1000 + "s");


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
