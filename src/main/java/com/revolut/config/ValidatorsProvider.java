package com.revolut.config;

import com.google.common.collect.Lists;
import com.google.inject.Provider;
import com.revolut.api.validator.Validator;
import com.revolut.api.validator.ValidatorFactory;
import com.revolut.dto.LocalTransferRequest;
import com.revolut.dto.TransferSearchRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class ValidatorsProvider implements Provider<Map<Class, List<Validator>>> {

    @Override
    public Map<Class, List<Validator>> get() {
        return new HashMap<Class, List<Validator>>(){{

            put(TransferSearchRequest.class,
                    Lists.newArrayList(
                            ValidatorFactory.getSearchRequestValidator()));

            put(LocalTransferRequest.class,
                    newArrayList(
                            ValidatorFactory.getLocalTransferRequestValidator()));

        }};
    }

}
