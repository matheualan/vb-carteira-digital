package com.api.carteira.digital.carteira.dto;

import java.math.BigDecimal;

public record CarteiraResponse(
        String nome,
        String descricao,
        BigDecimal saldo
) {
}
