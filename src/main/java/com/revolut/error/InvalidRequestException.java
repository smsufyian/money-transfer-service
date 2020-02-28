package com.revolut.error;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

public class InvalidRequestException extends TransferException {


    protected InvalidRequestException(Throwable throwable, String code, String message) {
        super(code, HTTP_BAD_REQUEST, throwable, message);
    }

    protected InvalidRequestException(String code, String message) {
        super(code, HTTP_BAD_REQUEST, null, message);
    }
}
