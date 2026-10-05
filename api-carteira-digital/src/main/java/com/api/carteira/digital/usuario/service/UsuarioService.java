package com.api.carteira.digital.usuario.service;

import com.api.carteira.digital.usuario.dto.UsuarioPatchRequest;
import com.api.carteira.digital.usuario.dto.UsuarioRequest;
import com.api.carteira.digital.usuario.dto.UsuarioResponse;
import com.api.carteira.digital.core.exception.RecursoNaoEncontradoException;
import com.api.carteira.digital.usuario.mapper.UsuarioMapper;
import com.api.carteira.digital.usuario.model.Usuario;
import com.api.carteira.digital.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;
//    private final UsuarioMapperMapperStruct usuarioMapper;

    @Transactional
    public UsuarioResponse salvarUsuario(UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
//        boolean matches = passwordEncoder.matches(usuarioRequest.senha(), usuario.getSenha()); //Verifica se as senhas são iguais
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public List<UsuarioResponse> salvarVarios(List<UsuarioRequest> listDTO) {
        List<Usuario> listUsuario = new ArrayList<>();

        for (UsuarioRequest dto : listDTO) {
            Usuario usuario = usuarioMapper.toEntity(dto);
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            listUsuario.add(usuario);
        }

        List<Usuario> usuariosSalvos = usuarioRepository.saveAll(listUsuario);
        return usuarioMapper.toListDTO(usuariosSalvos);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findByIdAndExcluidoFalse(id).orElseThrow(
                () -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioResponse> buscarUsuariosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuariosPage = usuarioRepository.findAllByExcluidoFalse(pageable); //Usando soft
        return usuariosPage.map(usuarioMapper::toResponse);
    }

    //    Deve retornar todos os usuarios apenas onde o campo excluído for false, ou seja, não excluídos
    @Transactional(readOnly = true)
    public List<UsuarioResponse> findAllByExcluidoFalse() {
        List<Usuario> allAndExcluidoFalse = usuarioRepository.findAllByExcluidoFalse();
        return usuarioMapper.toListDTO(allAndExcluidoFalse);
    }

    //    Deve retornar usuario por id onde o campo excluído for false, ou seja, não excluído
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorIdOndeExcluidoForFalse(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        return usuarioMapper.toResponse(usuario);
    }

    /*
       Anotação @Transactional ativa o Entity Manager/Persistence Context que o Hibernate ao buscar por Id joga a entidade nesse contexto,
       cria um snapshot do estado que veio do banco e trata a entidade como managed,
       dessa forma o hibernate faz o dirty checking verificando alguma alteração comparando o objeto em memória com o snapshot
       e salvando as alterações no banco sem precisar chamar o .save()
    */
    @Transactional
    public void softDelete(Long id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setExcluido(true);
    }

    /*
       @Transactional
       Diz que cada operação com o banco pertence a uma única unidade de trabalho, começa junto e termina junto
    */
    @Transactional
    public UsuarioResponse atualizarUsuario(Long id, UsuarioPatchRequest request) {
        Usuario usuario = buscarUsuarioPorId(id);
        Usuario usuarioAtualizado = usuarioMapper.toEntity(request, usuario);

        if (request.senha() != null && !request.senha().isBlank()) {
            usuarioAtualizado.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }

        return usuarioMapper.toResponse(usuarioAtualizado);
    }

//  Metodo usando Specification

}