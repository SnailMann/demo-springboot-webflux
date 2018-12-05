package com.snailmann.framework.proxy;


import com.snailmann.framework.annotation.ApiServer;
import com.snailmann.framework.bean.MethodInfo;
import com.snailmann.framework.bean.ServerInfo;
import com.snailmann.framework.rest.RestHandler;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现JDK动态代理实现代理类
 */
@Slf4j
public class JDKProxyCreator implements ProxyCreator {

    @Override
    public Object createProxy(Class<?> type) {


        //根据接口得到Api服务器信息
        ServerInfo serverInfo = extractServerInfo(type);

        //给每个代理类一个实现
        RestHandler restHandler = null;

        //因为每次额serverInfo都是一样的，所以不需要再下面多次传入，一次搞定
        restHandler.init(serverInfo);
        log.info("createProxy - type : {}", type);
        log.info("createProxy - server: {}", serverInfo);

        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{type}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //根据方法和参数得到调用信息

                MethodInfo methodInfo = extractMethodInfo(method, args);
                log.info("createProxy - method: {}", methodInfo);
                //调用rest
                return restHandler.invokeRest(methodInfo);
            }
        });
    }

    /**
     * 根据方法定义和调用参数得到调用的相关信息
     *
     * @param method
     * @param args
     * @return
     */
    private MethodInfo extractMethodInfo(Method method, Object[] args) {
        MethodInfo methodInfo = new MethodInfo();

        //获得url和请求类型
        extractUrlAndMethod(method, methodInfo);
        //获得参数
        extractRequestParamAndBody(method,methodInfo,args);

        return methodInfo;
    }

    /**
     * 从注解中获取url信息
     *
     * @param type
     * @return
     */
    private ServerInfo extractServerInfo(Class<?> type) {
        ServerInfo serverInfo = new ServerInfo();
        //从传入的class类型中，如果有该注解的情况下，得到ApiServer注解
        ApiServer annotation = type.getAnnotation(ApiServer.class);
        serverInfo.setUrl(annotation.value());
        return serverInfo;
    }


    /**
     * 得到请求的url和http方法
     *
     * @param method
     * @param methodInfo
     */
    private void extractUrlAndMethod(Method method, MethodInfo methodInfo) {

        //第一步得到方法注解的信息（请求类型和请求子url）
        Annotation[] annotations = method.getDeclaredAnnotations();
        Arrays.asList(annotations).forEach(annotation -> {
            //get请求
            if (annotation instanceof GetMapping) {
                GetMapping getMapping = (GetMapping) annotation;
                log.info("GetMapping value : {}", getMapping.value());
                methodInfo.setUrl(getMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.GET);

                //post请求
            } else if (annotation instanceof PostMapping) {
                PostMapping postMapping = (PostMapping) annotation;
                log.info("PostMapping value : {}", postMapping.value());
                methodInfo.setUrl(postMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.POST);

                //put请求
            } else if (annotation instanceof PutMapping) {
                PutMapping putMapping = (PutMapping) annotation;
                log.info("getMapping value : {}", putMapping.value());
                methodInfo.setUrl(putMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.PUT);

                //delete请求
            } else if (annotation instanceof DeleteMapping) {
                DeleteMapping deleteMapping = (DeleteMapping) annotation;
                log.info("getMapping value : {}", deleteMapping.value());
                methodInfo.setUrl(deleteMapping.value()[0]);
                methodInfo.setMethod(HttpMethod.DELETE);
            }

        });
    }

    /**
     * 得到请求的requestParam和requestBody
     * @param method
     * @param methodInfo
     * @param args
     */
    private void extractRequestParamAndBody(Method method, MethodInfo methodInfo, Object[] args) {
        //第二步解析参数注解和方法参数
        Parameter[] parameters = method.getParameters();

        //参数和值对应的map @PathVariable("aId") String bId   aId就是key,bId就是值
        Map<String, Object> params = new LinkedHashMap<>();
        methodInfo.setParams(params);

        //遍历该方法的所有参数
        for (int i = 0; i < parameters.length; i++) {

            //1. 看出参数是否带有@PathVariable注解
            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                //key是@PathVariable("aId")的value,key是用户的参数名称
                params.put(pathVariable.value(), args[i]);
            }

            //2. 看参数是否带有@RequsetBody
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                methodInfo.setBody((Mono<?>) args[i]);
            }

        }
    }
}
