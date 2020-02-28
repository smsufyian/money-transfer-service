package com.revolut.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TransactionStatus {

    OK(1),
    FAILED(0);

    @Getter
    int code;

    public static TransactionStatus fromCode(Integer code) {
        TransactionStatus[] values = values();
        for(TransactionStatus st : values) {
            if(st.code == code) {
                return st;
            }
        }

        throw new IllegalArgumentException("Invalid status code: "+code);
    }

}
