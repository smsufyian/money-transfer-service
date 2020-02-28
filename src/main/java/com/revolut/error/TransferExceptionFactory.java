package com.revolut.error;

public class TransferExceptionFactory {

    public static InvalidRequestException getInvalidRequestException(String code, String message) {
        return new InvalidRequestException(code, message);
    }

    public static InvalidRequestException getInvalidRequestException(Throwable e) {
        return new InvalidRequestException(e, null, e.getMessage());
    }

    public static InvalidRequestException getInvalidRequestException(String code, String message, Throwable e) {
        return new InvalidRequestException(e, code, message);
    }

    public static AccountNotFoundException accountNotFoundException(String code, int branchCode, long accountNumber) {
        return new AccountNotFoundException(code, String.format("Account %s%s not found",
                branchCode,
                accountNumber));
    }

    public static InsufficientFundsException getInsufficientFundsException(String code, String message) {
        return new InsufficientFundsException(code, message);
    }

    public static TransferNotFoundException getTransferNotFoundException(String code, String message) {
        return new TransferNotFoundException(code, message);
    }

    public static TooMuchAccountActivityException getTooMuchAccountActivityException(RuntimeException e) {
        return new TooMuchAccountActivityException(e);
    }

}
