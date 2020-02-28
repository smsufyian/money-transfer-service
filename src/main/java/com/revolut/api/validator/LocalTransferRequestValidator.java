package com.revolut.api.validator;

import com.revolut.dto.LocalTransferRequest;
import com.revolut.error.InvalidRequestException;

import static com.revolut.error.TransferExceptionFactory.getInvalidRequestException;

public class LocalTransferRequestValidator implements Validator<LocalTransferRequest> {

    public static final String TRANSFER_MISSING = "transfer.input.missing";
    public static final String TRANSFER_SENDER_BRANCH_MISSING = "transfer.input.senderBranch.missing";
    public static final String TRANSFER_SENDER_ACCOUNT_MISSING = "transfer.input.senderAccount.missing";
    public static final String TRANSFER_RECIPIENT_BRANCH_MISSING = "transfer.input.recipientBranch.missing";
    public static final String TRANSFER_RECIPIENT_ACCOUNT_MISSING = "transfer.input.recipientAccount.missing";
    public static final String TRANSFER_AMOUNT_MISSING = "transfer.input.amount.missing";
    public static final String TRANSFER_AMOUNT_INVALID = "transfer.input.amount.invalid";
    public static final String SAME_ACCOUNT = "transfer.input.sameAccount";

    protected LocalTransferRequestValidator() {

    }

    @Override
    public void validate(LocalTransferRequest request) throws InvalidRequestException {

        if (request == null) {
            throw getInvalidRequestException(TRANSFER_MISSING, "Transfer request can not be null");
        }

        if (request.getSenderBranchCode() == null) {
            throw getInvalidRequestException(TRANSFER_SENDER_BRANCH_MISSING, "Sender branch can not be null");
        }

        if (request.getSenderAccountNumber() == null) {
            throw getInvalidRequestException(TRANSFER_SENDER_ACCOUNT_MISSING, "Sender account can not be null");
        }

        if (request.getRecipientBranchCode() == null) {
            throw getInvalidRequestException(TRANSFER_RECIPIENT_BRANCH_MISSING, "Recipient branch can not be null");
        }

        if (request.getRecipientAccountNumber() == null) {
            throw getInvalidRequestException(TRANSFER_RECIPIENT_ACCOUNT_MISSING, "Recipient account can not be null");
        }

        if (request.getAmount() == null) {
            throw getInvalidRequestException(TRANSFER_AMOUNT_MISSING, "Amount can not be null");
        }

        if (request.getAmount() < 0) {
            throw getInvalidRequestException(TRANSFER_AMOUNT_INVALID, "Amount can not be below zero");
        }

        if (request.getRecipientBranchCode().equals(request.getSenderBranchCode()) &&
            request.getRecipientAccountNumber().equals(request.getSenderAccountNumber())) {
            throw getInvalidRequestException(SAME_ACCOUNT, "Sender and recipient accounts can not be same");
        }
    }



}
