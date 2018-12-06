package com.snailmann.framework.rest;

import com.snailmann.framework.bean.MethodInfo;
import com.snailmann.framework.bean.ServerInfo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

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

        Object result = null;

        WebClient.ResponseSpec request = this.webClient
                //http方法
                .method(methodInfo.getMethod())
                //url
                .uri(methodInfo.getUrl(),methodInfo.getParams())
                //返回值格式
                .accept(MediaType.APPLICATION_JSON)
                //发出请求
                .retrieve();

        //处理response的body
        if (methodInfo.isResultType()){
            result = request.bodyToFlux(methodInfo.getResultParamType());
        } else {
            result = request.bodyToMono(methodInfo.getResultParamType());
        }


        return result;
    }
}
