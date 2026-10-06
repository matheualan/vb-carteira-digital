package com.api.carteira.digital.usuario.model;

import com.api.carteira.digital.carteira.model.Carteira;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "id", "excluido", "dataCriacao", "dataAtualizacao" })
public class Usuario {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false) //, insertable = false) //estava ficando null quando ia salvar usuario junto com carteira, por isso colocado insertable = false
    private Boolean excluido = false;

    @Column(nullable = false, updatable = false) //insertable false = Hibernate omite o campo no insert / updatable false = nao inclui o campo em updates
    private LocalDateTime dataCriacao; //Quem gera é o banco na hora do insert

    @Column
    private LocalDateTime dataAtualizacao;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carteira carteira;

    public int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public String retornarIdade() {
        int idade = calcularIdade(this.dataNascimento);
        return String.format("%d anos", idade);
    }

    @PrePersist
    private void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.excluido = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

}