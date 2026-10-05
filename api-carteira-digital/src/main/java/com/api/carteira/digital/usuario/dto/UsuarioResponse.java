package com.api.carteira.digital.usuario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        String telefone,
        String cpf,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento
) {
}