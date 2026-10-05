package com.api.carteira.digital.core.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidExceptionDetails extends ExceptionDetails {

    private final String fields;
    private final String fieldsMessage;

}
