package com.revolut.dto;

import lombok.Data;

@Data
public class LocalTransferRequest {

    private Integer senderBranchCode;
    private Long senderAccountNumber;
    private Integer recipientBranchCode;
    private Long recipientAccountNumber;
    private Long amount;

}
