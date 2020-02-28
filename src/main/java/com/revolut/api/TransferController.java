package com.revolut.api;

import com.google.inject.Inject;
import com.revolut.dto.*;
import com.revolut.service.TransferService;
import spark.Request;
import spark.Response;

import static java.net.HttpURLConnection.HTTP_CREATED;

public class TransferController extends BaseController {


    public static final String TRANSFER_ID = "transferId";


    @Inject
    private TransferService transferService;


    public TransferResponse makeLocalTransfer(Request req, Response res) {
        LocalTransferRequest request = buildRequest(req.body(), LocalTransferRequest.class);

        TransferResponse transferResponse = transferService.makeLocalTransfer(request);

        res.status(HTTP_CREATED);
        return transferResponse;
    }

    public SearchTransferResponse searchTransfers(Request req, Response res) {
        TransferSearchRequest request = buildRequest(req.body(), TransferSearchRequest.class);

        SearchTransferResponse searchResult = transferService.searchTransfers(request);
        return searchResult;
    }

    public TransferDTO getTransfer(Request req, Response res) {
        Long transferId = id(req.params(TRANSFER_ID));
        TransferDTO transfer = transferService.getTransfer(transferId);
        return transfer;
    }

}
