package com.api.carteira.digital.usuario.model;

import com.api.carteira.digital.carteira.model.Carteira;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({ "id", "excluido", "dataCriacao", "dataAtualizacao" }) //Anotação sendo usada para testes
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Verificar por que é melhor usar IDENTITY do que AUTO no PostgreSQL
    private Long id;

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

    @Column(nullable = false, insertable = false) //estava ficando null quando ia salvar usuario junto com carteira, por isso colocado insertable = false
    private Boolean excluido = false;

//  Essa abordagem de insertable e updatable é para não precisar passar o valor desse campo e deixar o banco de dados gerar por meio do default current_timestamp
    @Column(nullable = false, insertable = false, updatable = false) //insertable false = Hibernate omite o campo no insert / updatable false = nao inclui o campo em updates
//  @CreationTimestamp Poderia ser uma solução onde o Hibernate geraria o valor e mandaria pro insert.
//  Resolve a problema de ler o valor de volta após o save(). pois com o banco gerando se der um getDataCriacao() em seguida retorna null até um novo find
    private LocalDateTime dataCriacao; //Quem gera é o banco na hora do insert

    @Column
    private LocalDateTime dataAtualizacao; //Se eu quiser salvar todas as datas que foi atualizado, como faço?

//    mappedBy = "usuario" indica que o relacionamento é bidirecional e que a entidade Carteira é a dona do relacionamento.
//    cascade = CascadeType.ALL indica que todas as operações (persist, merge, remove, refresh, detach) serão propagadas para a entidade Carteira.
//    orphanRemoval = true indica que se a entidade Usuario for removida, a entidade Carteira associada também será removida.
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Carteira> carteiras = new ArrayList<>();

    // Verificar se esses dois métodos são realmente necessários
    public void adicionarCarteira(Carteira carteira) {
        carteiras.add(carteira);
        carteira.setUsuario(this);
    }
    public void removerCarteira(Carteira carteira) {
        carteiras.remove(carteira);
        carteira.setUsuario(null);
    }

    public int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public String retornarIdade() {
        int idade = calcularIdade(this.dataNascimento);
        return String.format("%d anos", idade);
    }

}