package com.api.carteira.digital.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestHandlerException {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ExceptionDetails> handlerUsuarioNaoEncontradoException(RecursoNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDetails.builder()
                        .titulo("Recurso não encontrado")
                        .status(HttpStatus.NOT_FOUND.value())
                        .detalhe(e.getMessage())
                        .mensagemDev(e.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handleValidation(MethodArgumentNotValidException e) {
        List<ValidationExceptionDetails.CampoErro> erros = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> new ValidationExceptionDetails.CampoErro(field.getField(), field.getDefaultMessage()))
                .toList();

//        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
//        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
//        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationExceptionDetails.builder()
                        .titulo("Erro de validação de campos")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detalhe("Um ou mais campos estão inválidos.")
                        .mensagemDev(e.getClass().getName())
                        .erros(erros)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> handleDataIntegrity(DataIntegrityViolationException e) {
        log.warn("Violação de integridade do banco: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ExceptionDetails.builder()
                        .titulo("Conflito de dados")
                        .status(HttpStatus.CONFLICT.value())
                        .detalhe("Já existe registro com os dados informados")
                        .mensagemDev(e.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleGeneralException(Exception e) {
        log.error("Erro interno não tratado: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionDetails.builder()
                        .titulo("Erro interno do servidor")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .detalhe("Ocorreu um erro interno inesperado. Tente novamente mais tarde.")
                        .mensagemDev(e.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

}