package com.revolut.util;

public class RetryBackOffException extends RuntimeException {

    public RetryBackOffException(Throwable t) {
        super(t);
    }

}
