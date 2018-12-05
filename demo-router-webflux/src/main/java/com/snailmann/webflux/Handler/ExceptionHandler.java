package com.snailmann.webflux.Handler;

import com.snailmann.webflux.advice.InVaildUserNameException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * 这里是为了处理参数校验
 * aop的形式处理流处理过程中的异常
 * 当方法出现异常的时候，它就会进入WebExceptionHandler的handle方法里面
 *
 * 另外要注意的一点是，这里存在很多个ExceptionHandler，所以我们需要将优先级调高，才能让他进行工作
 * 这里至少到调到-2,数值越小，优先级越高
 */

@Component
@Order(-2)
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpResponse response = serverWebExchange.getResponse();
        //设置响应头400
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        //设置返回类型，可以返回json，这里为了简单，就只返回文本
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        //异常信息
        String msg = toStr(throwable);
        DataBuffer db = response.bufferFactory().wrap(msg.getBytes());
        return response.writeWith(Mono.just(db));
    }

    private String toStr(Throwable throwable) {
        //打印异常的时候，可以分为已知异常和未知异常
        //已知异常，这里只有一个
        if (throwable instanceof InVaildUserNameException) {
            InVaildUserNameException e = (InVaildUserNameException) throwable;
            return "错误参数 - " + e.getFieldName() + ":" + e.getFieldValue();

        }
        //未知异常,需要打印堆栈，方便定位
        else {

            throwable.printStackTrace();
            return throwable.toString();
        }

    }
}
