package com.revolut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchTransferResponse {

    private List<TransferDTO> transfers;

}
