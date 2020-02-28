package com.revolut.repository;

import com.revolut.dto.TransactionStatus;
import com.revolut.dto.TransferDTO;
import org.jooq.Record;
import org.jooq.RecordMapper;

import static com.revolut.domain.Tables.TRANSFERS;

public class TransferDTOMapper implements RecordMapper<Record, TransferDTO> {

    @Override
    public TransferDTO map(Record record) {
        return TransferDTO.builder()
                .id(record.getValue(TRANSFERS.ID))
                .amount(record.getValue(TRANSFERS.AMOUNT))
                .createdOn(record.getValue(TRANSFERS.CREATED_ON))
                .recipientName(record.getValue("recipientName").toString())
                .recipientAccount(record.getValue("recipientAccount").toString())
                .senderAccount(record.getValue("senderAccount").toString())
                .status(TransactionStatus.fromCode(record.getValue(TRANSFERS.STATUS).intValue()))
                .build();
    }

}
