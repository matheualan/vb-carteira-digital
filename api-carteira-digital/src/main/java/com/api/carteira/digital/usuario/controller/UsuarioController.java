package com.api.carteira.digital.usuario.controller;

import com.api.carteira.digital.usuario.dto.UsuarioPatchRequest;
import com.api.carteira.digital.usuario.dto.UsuarioRequest;
import com.api.carteira.digital.usuario.dto.UsuarioResponse;
import com.api.carteira.digital.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "API para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(
            summary = "Salva usuário",
            description = "Endpoint para salvar um novo usuário"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping()
    public ResponseEntity<UsuarioResponse> salvar(@RequestBody @Valid UsuarioRequest usuarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuarioRequest));
    }

    @Operation(
            summary = "Salva vários usuários",
            description = "Endpoint para salvar vários usuários de uma vez"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuários salvos com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping(path = "/")
    public ResponseEntity<List<UsuarioResponse>> salvarVarios(@RequestBody @Valid List<UsuarioRequest> listDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarVarios(listDTO));
    }

    @Operation(
            summary = "Busca usuários paginados (soft delete)",
            description = "Endpoint para buscar usuários de forma paginada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(path = "/")
    public ResponseEntity<Page<UsuarioResponse>> buscarUsuariosPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<UsuarioResponse> usuariosPage = usuarioService.buscarUsuariosPaginados(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(usuariosPage);
    }

    @Operation(
            summary = "Deleta usuário por ID (soft delete)",
            description = "Endpoint para realizar soft delete em um usuário específico pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Soft delete realizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping()
    public ResponseEntity<Void> softDelete(@RequestParam(value = "id") UUID id) {
        usuarioService.softDelete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Busca usuário por ID (soft delete)",
            description = "Endpoint para buscar um usuário específico pelo ID, considerando apenas usuários não excluídos (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
//    public ResponseEntity<UsuarioResponse> softFindById(@RequestParam(value = "id") Long id) {
    public ResponseEntity<UsuarioResponse> softFindById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.buscarPorIdOndeExcluidoForFalse(id));
    }

    @Operation(
            summary = "Busca todos os usuários (soft delete)",
            description = "Endpoint para buscar todos os usuários, considerando apenas os não excluídos (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping()
    public ResponseEntity<List<UsuarioResponse>> softFindAll() {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAllByExcluidoFalse());
    }

    @Operation(
            summary = "Atualiza usuário por ID",
            description = "Endpoint para atualizar um usuário específico pelo ID, considerando apenas usuários não excluídos (soft delete)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PatchMapping()
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestParam(value = "id") UUID id,
                                                            @RequestBody @Valid UsuarioPatchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.atualizarUsuario(id, request));
    }

}