package com.api.carteira.digital.usuario.mapper;

import com.api.carteira.digital.usuario.dto.UsuarioPatchRequest;
import com.api.carteira.digital.usuario.dto.UsuarioRequest;
import com.api.carteira.digital.usuario.dto.UsuarioResponse;
import com.api.carteira.digital.usuario.model.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequest request) {
        Usuario entity = new Usuario();
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        entity.setTelefone(request.telefone());
        entity.setCpf(request.cpf());
        entity.setSenha(request.senha());
        entity.setDataNascimento(request.dataNascimento());
        return entity;
    }

//     Refatorar: verificar se tem alguma forma de fazer sem tantos ifs
//     Usado para atualização, não precisa criar objeto novo, recebe no parâmetro um objeto (entidade) para atualizar
    public Usuario toEntity(UsuarioPatchRequest request, Usuario usuario) {
        if (request.nome() != null && !request.nome().isBlank()) {
            usuario.setNome(request.nome());
        }
        if (request.email().isPresent()) { //optional nao pode fazer != null pq ele recebe Optional.empty() se nao passar no JSON
            usuario.setEmail(request.email().get());
        }
        if (request.telefone() != null && !request.telefone().isBlank()) {
            usuario.setTelefone(request.telefone());
        }
        if (request.cpf().isPresent()) {
            usuario.setCpf(request.cpf().get());
        }
//        if (request.senha() != null && !request.senha().isBlank()) {
//            usuario.setSenha(request.senha());
//        }
        if (request.dataNascimento().isPresent()) {
            usuario.setDataNascimento(request.dataNascimento().get());
        }
//        request.dataNascimento().ifPresent(usuario::setDataNascimento);
        usuario.setDataAtualizacao(LocalDateTime.now());
        return usuario;
    }

    public UsuarioResponse toResponse(Usuario usuario) {
        Long id = usuario.getId();
        String nome = usuario.getNome();
        String email = usuario.getEmail();
        String telefone = usuario.getTelefone();
        String cpf = usuario.getCpf();
        LocalDate dataNascimento = usuario.getDataNascimento();
        return new UsuarioResponse(id, nome, email, telefone, cpf, dataNascimento);
    }

    public List<UsuarioResponse> toListDTO(List<Usuario> usuarios) {
        List<UsuarioResponse> listDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            listDTO.add(toResponse(usuario));
        }
        return listDTO;
    }

}