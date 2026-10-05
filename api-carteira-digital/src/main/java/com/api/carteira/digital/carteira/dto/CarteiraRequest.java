package com.api.carteira.digital.carteira.dto;

import java.math.BigDecimal;

public record CarteiraRequest(
        String nome,
        String descricao,
        BigDecimal saldo
) {
}