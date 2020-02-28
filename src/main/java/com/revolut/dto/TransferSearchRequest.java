package com.revolut.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferSearchRequest {


    private LocalDateTime start;

    private LocalDateTime end;

    private int pageNo = 1;



}
