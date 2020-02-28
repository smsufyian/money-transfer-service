package com.revolut.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.revolut.api.validator.Validator;
import spark.utils.StringUtils;

import java.util.List;
import java.util.Map;

import static com.revolut.error.TransferExceptionFactory.getInvalidRequestException;
import static java.util.Collections.EMPTY_LIST;

public class BaseController {


    public static final String TRANSFER_ID_MISSING = "transfer.id.missing";
    public static final String TRANSFER_ID_INVALID = "transfer.id.invalid";
    @Inject
    private Gson gson;

    @Inject
    private Map<Class, List<Validator>> validators;

    private final List<Validator> NO_VALIDATORS = EMPTY_LIST;


    final <T> T buildRequest(String req, Class<T> type) {
        try {
            T value = gson.fromJson(req, type);

            validators.getOrDefault(type, NO_VALIDATORS)
                    .stream()
                    .forEach(v -> v.validate(value));

            return value;
        } catch (JsonSyntaxException e) {
            throw getInvalidRequestException(e);
        }
    }


    final Long id(String id) {

        if (StringUtils.isBlank(id)) {
            throw getInvalidRequestException(TRANSFER_ID_MISSING, String.format("Invalid request id: %s", id));
        }

        try {
            return Long.parseLong(id);
        } catch (IllegalArgumentException e) {
            throw getInvalidRequestException(TRANSFER_ID_INVALID, String.format("Invalid request id: %s", id), e);
        }
    }

}
