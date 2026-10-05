package com.api.carteira.digital.carteira.model;

import com.api.carteira.digital.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "carteira")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "id",  "usuario" })
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String descricao;
    private BigDecimal saldo;

    @Column(nullable = false) // insertable = false, updatable = false
    private LocalDateTime dataCriacao; // = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") //, nullable = false
    private Usuario usuario;

    public Carteira(String nome, String descricao, BigDecimal saldo) {
        this.nome = nome;
        this.descricao = descricao;
        this.saldo = saldo;
    }

    @PrePersist
    private void definirCampos() {
        this.dataCriacao = LocalDateTime.now();
    }

}