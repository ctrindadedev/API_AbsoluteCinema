package com.ufrn.imd.cinema.controllers.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health", description = "Verificação de disponibilidade da API")
public class HealthController {

    @Operation(summary = "Verificar disponibilidade", description = "Retorna OK se a API estiver operante")
    @ApiResponse(responseCode = "200", description = "API disponível")
    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("OK");
    }
}
