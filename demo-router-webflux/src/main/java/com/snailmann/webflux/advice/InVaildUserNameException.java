package com.snailmann.webflux.advice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InVaildUserNameException extends RuntimeException {


    private String fieldName;
    private String fieldValue;


    public InVaildUserNameException() {
        super();
    }

    public InVaildUserNameException(String message) {
        super(message);
    }

    public InVaildUserNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InVaildUserNameException(Throwable cause) {
        super(cause);
    }

    public InVaildUserNameException(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    protected InVaildUserNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
