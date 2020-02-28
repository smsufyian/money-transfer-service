package com.revolut.api.validator;

import com.revolut.dto.TransferSearchRequest;
import com.revolut.error.InvalidRequestException;

import static com.revolut.error.TransferExceptionFactory.getInvalidRequestException;

public class SearchRequestValidator implements Validator<TransferSearchRequest> {

    public static final String SEARCH_MISSING = "search.input.missing";
    public static final String SEARCH_END_MISSING = "search.input.end.missing";
    public static final String SEARCH_START_MISSING = "search.input.start.missing";

    protected SearchRequestValidator() {

    }

    @Override
    public void validate(TransferSearchRequest request) throws InvalidRequestException {
        if (request == null) {
            throw getInvalidRequestException(SEARCH_MISSING, "Search request can not be null");
        }

        if (request.getEnd() == null) {
            throw getInvalidRequestException(SEARCH_END_MISSING, "End date can not be null");
        }

        if (request.getStart() == null) {
            throw getInvalidRequestException(SEARCH_START_MISSING, "End date can not be null");
        }
    }


}
