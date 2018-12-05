package com.snailmann.webflux.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * 统一异常处理切面
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 默认参数Vaild异常统一处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBindException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(toStr(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 自定义违规名称异常统一处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InVaildUserNameExecption.class)
    public ResponseEntity handleInVaildUserNameExeption(InVaildUserNameExecption e) {
        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param execption
     * @return
     */
    private String toStr(InVaildUserNameExecption execption) {
        return execption.getFieldName() + "不允许为:" + execption.getFieldValue();
    }

    /**
     * 把校验异常转换为字符串
     *
     * @param ex
     * @return
     */
    private String toStr(BindingResult ex) {
        return ex.getFieldErrors().stream()
                //将FieldErrors转换成e.getField() + ":" + e.getDefaultMessage()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                //然后将每个e.getField() + ":" + e.getDefaultMessage()累加成一个完整的字符串（用换行隔开）
                .reduce("", (s1, s2) -> s1 + "\n" + s2);
    }


}
