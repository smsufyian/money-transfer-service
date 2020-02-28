package com.revolut.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revolut.dto.*;
import com.revolut.repository.TransferRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class TransferService {

    @Inject
    private TransferRepository transferRepository;

    public TransferResponse makeLocalTransfer(LocalTransferRequest request) {
        log.debug("creating transfer: {}", request);

        TransferResponse response = transferRepository.createTransfer(request);

        log.debug("transfer created: {}", response.getId());
        log.trace("transfer created: {}", response);
        return response;
    }

    public SearchTransferResponse searchTransfers(TransferSearchRequest request) {
        log.debug("searching transfers: {}", request);

        SearchTransferResponse transfers = transferRepository.searchTransfers(request);

        log.debug("transfers returnedd: {}", transfers.getTransfers().size());
        log.trace("transfers returned: {}", transfers);
        return transfers;
    }

    public TransferDTO getTransfer(Long id) {
        log.debug("getting transfer: {}", id);

        TransferDTO transfer = transferRepository.getTransfer(id);

        log.debug("returning transfer: {}", transfer.getId());
        log.trace("returning transfer: {}", transfer);
        return transfer;


    }
}
