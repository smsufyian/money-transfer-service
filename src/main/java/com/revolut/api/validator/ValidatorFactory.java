package com.revolut.api.validator;

import com.revolut.dto.LocalTransferRequest;
import com.revolut.dto.TransferSearchRequest;

public class ValidatorFactory {

    public static Validator<TransferSearchRequest> getSearchRequestValidator() {
        return new SearchRequestValidator();
    }

    public static Validator<LocalTransferRequest> getLocalTransferRequestValidator() {
        return new LocalTransferRequestValidator();
    }

}
