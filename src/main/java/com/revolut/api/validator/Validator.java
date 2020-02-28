package com.revolut.api.validator;

import com.revolut.error.InvalidRequestException;

import java.lang.reflect.ParameterizedType;

public interface Validator<T> {

    void validate(T request) throws InvalidRequestException;

    default Class<T> supportedType() {
        return (Class<T>)
                ((ParameterizedType)getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
    }

}
