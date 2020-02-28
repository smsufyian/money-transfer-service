package com.revolut.error;

import static java.net.HttpURLConnection.HTTP_CONFLICT;

public class InsufficientFundsException extends TransferException {


    protected InsufficientFundsException(Throwable throwable, String code, String message) {
        super(code, HTTP_CONFLICT, throwable, message);
    }

    protected InsufficientFundsException(String code, String message) {
        super(code, HTTP_CONFLICT, null, message);
    }
}
