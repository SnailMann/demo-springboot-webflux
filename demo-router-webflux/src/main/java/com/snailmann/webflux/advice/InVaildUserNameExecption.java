package com.snailmann.webflux.advice;

import lombok.Data;

@Data
public class InVaildUserNameExecption extends RuntimeException {


    private String fieldName;
    private String fieldValue;


    public InVaildUserNameExecption() {
        super();
    }

    public InVaildUserNameExecption(String message) {
        super(message);
    }

    public InVaildUserNameExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public InVaildUserNameExecption(Throwable cause) {
        super(cause);
    }

    public InVaildUserNameExecption(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    protected InVaildUserNameExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
