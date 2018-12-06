package com.snailmann.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 方法调用信息类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private HttpMethod method;

    /**
     * 请求参数
     * 类似PathVariable其实本质是一个key-value值
     * @PathVariable("aId") String bId   aId就是key,bId就是值
     */
    private Map<String,Object> params;

    /**
     * 请求body
     */
    private Object body;


    /**
     * 结果类型是Flux还是Mono ,true是flux
     */
    private boolean resultType;


    /**
     * 返回对象的类型
     */
    private Class<?> resultParamType;
}
