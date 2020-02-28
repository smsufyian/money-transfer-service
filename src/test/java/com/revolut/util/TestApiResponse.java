package com.revolut.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TestApiResponse {

    private Map<String, Object> content;
    private int httpStatus;

}
