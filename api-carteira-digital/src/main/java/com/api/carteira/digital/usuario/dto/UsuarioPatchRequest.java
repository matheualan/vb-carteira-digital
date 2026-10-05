package com.api.carteira.digital.usuario.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Optional;

public record UsuarioPatchRequest(
        @Size(min = 3, max = 150, message = "O nome deve ter entre 3 e 150 caracteres")
        String nome,

        Optional<@Email(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "O email deve ser válido"
        ) String> email,

        @Size(min = 10, max = 15, message = "O telefone deve ter entre 10 e 15 caracteres")
        String telefone,

        Optional<@Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos") @Pattern(regexp = "^[0-9]{11}$", message = "O CPF deve conter apenas números")
                String> cpf,

        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*]).{8,64}$", //Regex valida se tem uma maiúscula, um número, um caractere especial e tamanho de 8 a 64
                message = "A senha deve conter entre 8 e 64 caracteres, incluindo pelo menos uma letra maiúscula, um número e um caractere especial."
        )
        String senha,

        @JsonFormat(pattern = "dd/MM/yyyy")
        Optional<LocalDate> dataNascimento
) {
}