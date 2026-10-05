package com.api.carteira.digital.core.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {

    public record CampoErro(String campo, String mensagem) {}

    private List<CampoErro> erros;

}