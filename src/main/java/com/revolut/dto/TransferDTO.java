package com.revolut.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class TransferDTO {

    private Long id;
    private Long amount;
    private TransactionStatus status;
    private LocalDateTime createdOn;
    private String senderAccount;
    private String recipientAccount;
    private String recipientName;

    public TransferDTO(Long id, Long amount, TransactionStatus status, LocalDateTime createdOn, String senderAccount, String recipientAccount, String recipientName) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.createdOn = createdOn;
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.recipientName = recipientName;
    }
}
