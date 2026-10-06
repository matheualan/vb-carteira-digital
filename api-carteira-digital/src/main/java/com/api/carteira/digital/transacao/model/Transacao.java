package com.api.carteira.digital.transacao.model;

import com.api.carteira.digital.carteira.model.Carteira;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transacao {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carteira_origem_id")
    private Carteira carteiraOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carteira_destino_id")
    private Carteira carteiraDestino;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private StatusTransacao statusTransacao;

//    @Version
//    private Long versao;

    @PrePersist
    private void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

}