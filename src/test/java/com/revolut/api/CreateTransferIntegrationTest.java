package com.revolut.api;

import com.revolut.dto.LocalTransferRequest;
import com.revolut.dto.TransactionStatus;
import com.revolut.util.TestApiResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import static com.revolut.api.validator.LocalTransferRequestValidator.SAME_ACCOUNT;
import static com.revolut.api.validator.LocalTransferRequestValidator.TRANSFER_AMOUNT_INVALID;
import static com.revolut.repository.TransferRepository.*;
import static com.revolut.util.TestApiUtil.post;
import static java.net.HttpURLConnection.*;

@DisplayName("Test create transfer api")
public class CreateTransferIntegrationTest extends IntegrationTestBase {


    @Test
    @DisplayName("to successfully transfer amount")
    public void testCreateTransfer() throws Exception {
        LocalTransferRequest req = createLocalTransferRequest(500l, 123401l, 123402L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_CREATED, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(TransactionStatus.OK.name(), response.getContent().get("status"), "unexpected transaction status");
    }

    @Test
    @DisplayName("to fail transfer because sender account doesnt exeist")
    public void testCreateTransferWithSenderDoesnExist() throws Exception {
        LocalTransferRequest req = createLocalTransferRequest(500l, 999999L, 123402L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_NOT_FOUND, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(SENDER_MISSING, response.getContent().get("code"), "unexpected error code");
    }

    @Test
    @DisplayName("to fail transfer because sender recipient doesnt exeist")
    public void testCreateTransferWithRecipientDoesnExist() throws Exception {
        LocalTransferRequest req = createLocalTransferRequest(500l, 123401l, 999999L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_NOT_FOUND, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(RECIPIENT_MISSING, response.getContent().get("code"), "unexpected error code");
    }

    @Test
    @DisplayName("to fail transfer with insufficient funds")
    public void testCreateTransferWithInsufficientFunds() throws Exception {
        LocalTransferRequest req = createLocalTransferRequest(99999999999999999l, 123401l, 123402L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_CONFLICT, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(SENDER_FUNDS_INSUFFICIENT, response.getContent().get("code"), "unexpected error code");
    }

    @Test
    @DisplayName("to first transfer amount to run out of funds, then get insufficient funds error")
    public void testCreateTransferWithInsufficientFundsAfterAnAttempt() throws Exception {
        LocalTransferRequest req = createLocalTransferRequest(10000L, 123401l, 123402L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_CREATED, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(TransactionStatus.OK.name(), response.getContent().get("status"), "unexpected transaction status");

        TestApiResponse response2 = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_CONFLICT, response2.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(SENDER_FUNDS_INSUFFICIENT, response2.getContent().get("code"), "unexpected error code");
    }


    @Test
    @DisplayName("to fail when amount is less than 0")
    public void testCreateTransferWithLessThan0Amount() throws Exception {

        TestApiResponse response = post("http://localhost:2020/transfer", createLocalTransferRequest(-10L, 123401l, 123402L));

        Assertions.assertEquals(HTTP_BAD_REQUEST, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(TRANSFER_AMOUNT_INVALID, response.getContent().get("code"), "unexpected error code");
    }

    @Test
    @DisplayName("to fail when sender and receiver are same")
    public void testCreateTransferWithValidationErrors() throws Exception {
        LocalTransferRequest req = createLocalTransferRequest(10L, 123401l, 123401L);
        req.setRecipientBranchCode(req.getSenderBranchCode());

        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_BAD_REQUEST, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(SAME_ACCOUNT, response.getContent().get("code"), "unexpected error code");
    }

    private LocalTransferRequest createLocalTransferRequest(Long amount, Long senderAcc, Long recipientAcc) {
        LocalTransferRequest req = new LocalTransferRequest();
        req.setAmount(amount);
        req.setSenderBranchCode(901);
        req.setSenderAccountNumber(senderAcc);
        req.setRecipientBranchCode(902);
        req.setRecipientAccountNumber(recipientAcc);
        return req;
    }

}
