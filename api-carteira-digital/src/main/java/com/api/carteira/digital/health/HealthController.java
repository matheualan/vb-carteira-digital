package com.api.carteira.digital.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/health")
@Tag(name = "Health Check", description = "Endpoints para verificação de saúde da aplicação")
public class HealthController {

    @Operation(
            summary = "Verificar saúde da aplicação",
            description = "Retorna o status atual da API para monitoramento de infra, load balancers e ferramentas de observabilidade"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna 200 se o serviço estiver operando normalmente"),
            @ApiResponse(responseCode = "500", description = "Retorna 500 se ocorrer um erro no servidor")
    })
    @GetMapping()
    public ResponseEntity<String> health() {
        return ResponseEntity.status(HttpStatus.OK).body("Aplicação saudável. Tudo OK funcionando!");
    }

}