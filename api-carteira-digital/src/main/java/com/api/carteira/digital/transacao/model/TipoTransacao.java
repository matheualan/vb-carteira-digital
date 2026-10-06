package com.api.carteira.digital.transacao.model;

public enum TipoTransacao {
    DEPOSITO, //Somente carteira destino
    SAQUE, //Somente carteira origem
    TRANSFERENCIA
}