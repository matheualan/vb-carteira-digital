package com.api.carteira.digital.usuario.controller;

import com.api.carteira.digital.carteira.service.CarteiraService;
import com.api.carteira.digital.core.exception.RecursoNaoEncontradoException;
import com.api.carteira.digital.usuario.model.Usuario;
import com.api.carteira.digital.usuario.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/teste")
@RequiredArgsConstructor
@Tag(name = "Testes", description = "Controller para testar endpoints")
public class TesteController {

    private final UsuarioRepository usuarioRepository;
    private final CarteiraService carteiraService;

    @Operation(
            summary = "Busca usuário por ID",
            description = "Endpoint para buscar um usuário pelo seu ID"
    )
    @GetMapping
    public ResponseEntity<Usuario> getUsuarioEntidade(@RequestParam("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id)));
    }

    @Operation(
            summary = "Salva usuário",
            description = "Endpoint para salvar um novo usuário"
    )
    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
        usuario.getCarteira().setUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
    }

//    @PostMapping("/")
//    public ResponseEntity<Usuario> salvarUsuarioComCarteira(@RequestBody Usuario entity) {
//        System.out.println("1- ENTROU NO METODO , VEJA AI =====================================================================");
//
//        Carteira carteira = new Carteira(
//                entity.getCarteiras().get(0).getNome(),
//                entity.getCarteiras().get(0).getDescricao(),
//                entity.getCarteiras().get(0).getSaldo()
//        );
//        carteira.setUsuario(entity);
//
//        System.out.println("[DADOS DA CARTEIRA] Nome: " + carteira.getNome() +
//                ",\t Descrição: " + carteira.getDescricao() +
//                ",\t Saldo: " + carteira.getSaldo() +
//                ",\t Usuário: " + carteira.getUsuario().getNome());
//
//        entity.getCarteiras().clear();
//        entity.getCarteiras().add(carteira);
//
//        Usuario usuario = usuarioRepository.save(entity);
//        return ResponseEntity.status(201).body(usuario);
//    }

}