package com.revolut.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferResponse {

    private Long id;
    private TransactionStatus status;

}
