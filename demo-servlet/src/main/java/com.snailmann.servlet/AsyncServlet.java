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
 * 模拟异步Servlet
 */
@WebServlet(urlPatterns = "/async/servlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //开启异步
        AsyncContext asyncContext = null;
        if (request.isAsyncSupported()) {
            asyncContext = request.startAsync();
        }
        //使用CompletableFuture来模拟异步,传入的request和resonse是
        AsyncContext finalAsyncContext = asyncContext;
        CompletableFuture.runAsync(() ->
                doSomeThing(finalAsyncContext, finalAsyncContext.getRequest(), finalAsyncContext.getResponse()));
        System.out.println("异步调用执行完毕");

    }

    private void doSomeThing(AsyncContext asyncContext, ServletRequest request, ServletResponse response) {
        System.out.println("request:" + request.getClass() + " and reponse" + response.getClass());
        Long startTime = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sequence: " + count.incrementAndGet() + " spend :" + (double) (System.currentTimeMillis() - startTime) / 1000 + "s");
        //异步调用结束
        asyncContext.complete();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
