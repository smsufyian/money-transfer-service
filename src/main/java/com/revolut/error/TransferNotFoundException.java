package com.revolut.error;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class TransferNotFoundException extends TransferException {


    protected TransferNotFoundException(Throwable throwable, String code, String message) {
        super(code, HTTP_NOT_FOUND, throwable, message);
    }

    protected TransferNotFoundException(String code, String message) {
        super(code, HTTP_NOT_FOUND, null, message);
    }
}
