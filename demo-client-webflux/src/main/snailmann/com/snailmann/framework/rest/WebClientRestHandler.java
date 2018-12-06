package com.snailmann.framework.rest;

import com.snailmann.framework.bean.MethodInfo;
import com.snailmann.framework.bean.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientRestHandler implements RestHandler {

    private WebClient webClient;

    /**
     * 初始化webclient
     *
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 处理Restful请求
     *
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {

        Object result;

        WebClient.RequestBodySpec request = this.webClient
                //http方法
                .method(methodInfo.getMethod())
                //url
                .uri(methodInfo.getUrl(), methodInfo.getParams())
                //返回值格式
                .accept(MediaType.APPLICATION_JSON);
        //发出请求

        //判断是否带有body，如果带有body，就要执行body方法再发送
        WebClient.ResponseSpec retrieve;
        if (methodInfo.getBody() != null) {

            retrieve = request.body(Mono.just(methodInfo.getBody()), methodInfo.getBodyParamType()).retrieve();
        } else {
            retrieve = request.retrieve();
        }

        //处理异常
        retrieve.onStatus(status -> status.value() == 404,clientResponse -> Mono.just(new RuntimeException("Not Found")));

        //发送之后得到了response
        //处理response的body
        if (methodInfo.isResultType()) {
            result = retrieve.bodyToFlux(methodInfo.getResultParamType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getResultParamType());
        }


        return result;
    }
}
