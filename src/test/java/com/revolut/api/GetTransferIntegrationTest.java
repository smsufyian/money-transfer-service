package com.revolut.api;

import com.revolut.dto.LocalTransferRequest;
import com.revolut.dto.TransactionStatus;
import com.revolut.dto.TransferSearchRequest;
import com.revolut.util.TestApiResponse;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.revolut.util.TestApiUtil.get;
import static com.revolut.util.TestApiUtil.post;
import static java.net.HttpURLConnection.*;

@DisplayName("Test get and search transfer api")
public class GetTransferIntegrationTest extends IntegrationTestBase {


    @Test
    @DisplayName("to successfully get transfer after creating it")
    public void testGetTransfer() throws Exception {
        LocalTransferRequest req = new LocalTransferRequest();
        req.setAmount(5647L);
        req.setSenderBranchCode(901);
        req.setSenderAccountNumber(123401L);
        req.setRecipientBranchCode(902);
        req.setRecipientAccountNumber(123402L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_CREATED, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(TransactionStatus.OK.name(), response.getContent().get("status"), "unexpected transaction status");


        Double id = (Double) response.getContent().get("id");
        response = get("http://localhost:2020/transfer/"+id.intValue());

        Assertions.assertEquals(HTTP_OK, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(req.getAmount().doubleValue(), response.getContent().get("amount"), "unexpected http status");
        Assertions.assertEquals(String.format("%s%s", req.getSenderBranchCode(), req.getSenderAccountNumber()),
                response.getContent().get("senderAccount"), "unexpected http status");
        Assertions.assertEquals(String.format("%s%s", req.getRecipientBranchCode(), req.getRecipientAccountNumber()),
                response.getContent().get("recipientAccount"),"unexpected http status");
        Assertions.assertEquals(TransactionStatus.OK.name(),
                response.getContent().get("status"), "unexpected http status");

    }

    @Test
    @DisplayName("to successfully get transfer after creating it")
    public void testGetTransferTransferNotFound() throws Exception {
        Assertions.assertEquals(HTTP_NOT_FOUND, get("http://localhost:2020/transfer/1").getHttpStatus(), "unexpected http status");

    }


    @Test
    @DisplayName("to successfully search transfer with dates after creating it")
    public void testSearchTransfer() throws Exception {
        LocalTransferRequest req = new LocalTransferRequest();
        req.setAmount(5647L);
        req.setSenderBranchCode(901);
        req.setSenderAccountNumber(123401L);
        req.setRecipientBranchCode(902);
        req.setRecipientAccountNumber(123402L);


        TestApiResponse response = post("http://localhost:2020/transfer", req);

        Assertions.assertEquals(HTTP_CREATED, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(TransactionStatus.OK.name(), response.getContent().get("status"), "unexpected transaction status");

        TransferSearchRequest searchReq = new TransferSearchRequest();
        searchReq.setStart(LocalDateTime.now().minusMinutes(5));
        searchReq.setEnd(LocalDateTime.now().plusMinutes(5));

        Object expectedTransferId = response.getContent().get("id");

        response = post("http://localhost:2020/transfer/search", searchReq);


        Assertions.assertEquals(HTTP_OK, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(1, ((List) response.getContent().get("transfers")).size(), "unexpected http status");
        Object actualTransferId = ((Map) ((List) response.getContent().get("transfers")).get(0)).get("id");
        Assertions.assertEquals(expectedTransferId, actualTransferId, "unexpected transfer result");


    }

    @Test
    @DisplayName("to successfully search transfer with dates after creating it")
    public void testSearchTransferWIth0Results() throws Exception {

        TransferSearchRequest searchReq = new TransferSearchRequest();
        searchReq.setStart(LocalDateTime.now().minusMinutes(5));
        searchReq.setEnd(LocalDateTime.now().plusMinutes(5));


        TestApiResponse response = post("http://localhost:2020/transfer/search", searchReq);


        Assertions.assertEquals(HTTP_OK, response.getHttpStatus(), "unexpected http status");
        Assertions.assertEquals(0, ((List) response.getContent().get("transfers")).size(), "unexpected http status");

    }


}
