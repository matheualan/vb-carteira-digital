package com.api.carteira.digital.usuario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
//        String senha,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento
) {
}