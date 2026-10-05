package com.api.carteira.digital.carteira.controller;

import com.api.carteira.digital.carteira.dto.CarteiraRequest;
import com.api.carteira.digital.carteira.dto.CarteiraResponse;
import com.api.carteira.digital.carteira.service.CarteiraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/carteira")
@RequiredArgsConstructor
@Tag(name = "Carteira", description = "API para gerenciamento de carteiras") //Define o titulo swagger dos endpoints controller
public class CarteiraController {

    private final CarteiraService carteiraService;

//  Refatorar este endpoint para criar carteira a partir de usuário já existente, ou seja,
//  usuário que já tem conta e já tem carteiras mas quer adicionar uma a mais onde deve criar uma carteira e associar um usuário a ela
    @PostMapping
    public ResponseEntity<CarteiraResponse> criarCarteira(@RequestBody CarteiraRequest request) {
        return ResponseEntity.status(201).body(carteiraService.criarCarteira(request));
    }

    @GetMapping
    public ResponseEntity<List<CarteiraResponse>> listarCarteiras() {
        return ResponseEntity.ok(carteiraService.listarCarteiras());
    }

}