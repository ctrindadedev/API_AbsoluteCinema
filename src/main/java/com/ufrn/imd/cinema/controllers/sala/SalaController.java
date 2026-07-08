package com.ufrn.imd.cinema.controllers.sala;

import com.ufrn.imd.cinema.dtos.sala.SalaDtoReq;
import com.ufrn.imd.cinema.dtos.sala.SalaDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.sala.Sala;
import com.ufrn.imd.cinema.services.sala.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@Tag(name = "Salas", description = "Gerenciamento de salas de exibição")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @Operation(summary = "Cadastrar sala", description = "Registra uma nova sala de exibição")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sala criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<SalaDtoRes> criarSala(@Valid @RequestBody SalaDtoReq dto) {
        Sala sala = dto.toModel();
        salaService.registrarNovaSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(SalaDtoRes.from(sala));
    }

    @Operation(summary = "Buscar sala por id", description = "Retorna os dados de uma sala a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sala encontrada"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SalaDtoRes> buscarPorId(@PathVariable int id) {
        Sala sala = salaService.buscarPorId(id);
        return ResponseEntity.ok(SalaDtoRes.from(sala));
    }

    @Operation(summary = "Listar salas", description = "Retorna todas as salas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<SalaDtoRes>> listarTodas() {
        List<SalaDtoRes> salas = salaService.listarTodas().stream()
                .map(SalaDtoRes::from)
                .toList();
        return ResponseEntity.ok(salas);
    }

    @Operation(summary = "Remover sala", description = "Exclui uma sala de exibição")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sala removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable int id) {
        salaService.deletarSala(id);
        return ResponseEntity.noContent().build();
    }
}
