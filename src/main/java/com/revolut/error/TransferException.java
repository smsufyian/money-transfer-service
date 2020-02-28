package com.revolut.error;

import lombok.Getter;

@Getter
public abstract class TransferException extends RuntimeException {

    private int httpStatus;

    /**
     * used for translation
     */
    private String code;

    protected TransferException(String code, int httpStatus, Throwable throwable, String message) {
        super(message, throwable);
        this.httpStatus = httpStatus;
        this.code = code;
    }


}
