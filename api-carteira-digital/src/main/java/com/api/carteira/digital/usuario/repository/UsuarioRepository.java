package com.api.carteira.digital.usuario.repository;

import com.api.carteira.digital.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

//    derived querys

//  Exemplo de como seria a query JPQL
//  @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.excluido = false")
    Optional<Usuario> findByIdAndExcluidoFalse(UUID id);

//  Exemplo de como seria a query JPQL
//  @Query("SELECT u FROM Usuarios u WHERE u.excluido = false")
    List<Usuario> findAllByExcluidoFalse();

    Page<Usuario> findAllByExcluidoFalse(Pageable pageable);

//    List<Usuario> findByNomeContainingIgnoreCase(String nome);

}