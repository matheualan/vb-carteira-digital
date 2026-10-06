package com.api.carteira.digital.carteira.model;

import com.api.carteira.digital.transacao.model.Transacao;
import com.api.carteira.digital.usuario.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carteira")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id", "usuario", "origemTransacao", "destinoTransacao"})
public class Carteira {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @Column(nullable = false) // insertable = false, updatable = false
    private LocalDateTime dataCriacao; // = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY) //Verificar se precisa ter o FetchType.LAZY em @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true) //, nullable = false //unique garante usar único id
    private Usuario usuario;

    //    Para ter um atalho para poder buscar todas as transações onde teve essa carteira como origem
    @OneToMany(mappedBy = "carteiraOrigem", fetch = FetchType.LAZY)
    private List<Transacao> origemTransacao = new ArrayList<>();

    //    Para ter um atalho para poder buscar todas as transações onde teve essa carteira como destino
    @OneToMany(mappedBy = "carteiraDestino", fetch = FetchType.LAZY)
    private List<Transacao> destinoTransacao = new ArrayList<>();

    @Version
    private Long versao;

    public Carteira(String nome, BigDecimal saldo) {
        this.nome = nome;
        this.saldo = saldo;
    }

    @PrePersist
    private void definirCampos() {
        this.dataCriacao = LocalDateTime.now();
    }

}