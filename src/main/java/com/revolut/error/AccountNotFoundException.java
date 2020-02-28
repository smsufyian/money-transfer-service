package com.revolut.error;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

public class AccountNotFoundException extends TransferException {


    protected AccountNotFoundException(Throwable throwable, String code, String message) {
        super(code, HTTP_NOT_FOUND, throwable, message);
    }

    protected AccountNotFoundException(String code, String message) {
        super(code, HTTP_NOT_FOUND, null, message);
    }


}
