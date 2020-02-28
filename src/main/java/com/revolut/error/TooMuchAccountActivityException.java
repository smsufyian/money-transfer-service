package com.revolut.error;

import static java.net.HttpURLConnection.HTTP_UNAVAILABLE;

public class TooMuchAccountActivityException extends TransferException {


    public static final String ERROR_CODE = "transfer.account.activityExceeded";
    public static final String ERROR_MESSAGE = "There's too much activity on the account";

    protected TooMuchAccountActivityException(Throwable throwable) {
        super(ERROR_CODE, HTTP_UNAVAILABLE, throwable, ERROR_MESSAGE);
    }

    protected TooMuchAccountActivityException() {
        super(ERROR_CODE, HTTP_UNAVAILABLE, null, "There's too much activity on the account");
    }
}
