package com.ufrn.imd.cinema.controllers.ingresso;

import com.ufrn.imd.cinema.dtos.ingresso.IngressoDtoReq;
import com.ufrn.imd.cinema.dtos.ingresso.IngressoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import com.ufrn.imd.cinema.services.ingresso.IngressoService;
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
@RequestMapping("/api/ingressos")
@Tag(name = "Ingressos", description = "Gerenciamento de ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @Operation(summary = "Cadastrar ingresso", description = "Registra um novo ingresso vinculado a um pedido, assento e sessão")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ingresso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<IngressoDtoRes> criarIngresso(@Valid @RequestBody IngressoDtoReq dto) {
        Ingresso ingresso = dto.toModel();
        ingressoService.registrarNovoIngresso(ingresso);
        return ResponseEntity.status(HttpStatus.CREATED).body(IngressoDtoRes.from(ingresso));
    }

    @Operation(summary = "Buscar ingresso por id", description = "Retorna os dados de um ingresso a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingresso encontrado"),
            @ApiResponse(responseCode = "404", description = "Ingresso não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<IngressoDtoRes> buscarPorId(@PathVariable int id) {
        Ingresso ingresso = ingressoService.buscarPorId(id);
        return ResponseEntity.ok(IngressoDtoRes.from(ingresso));
    }

    @Operation(summary = "Listar ingressos", description = "Retorna todos os ingressos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de ingressos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<IngressoDtoRes>> listarTodos() {
        List<IngressoDtoRes> ingressos = ingressoService.listarTodos().stream()
                .map(IngressoDtoRes::from)
                .toList();
        return ResponseEntity.ok(ingressos);
    }

    @Operation(summary = "Atualizar ingresso", description = "Atualiza os dados de um ingresso existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ingresso atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Ingresso não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<IngressoDtoRes> atualizarIngresso(@PathVariable int id, @Valid @RequestBody IngressoDtoReq dto) {
        Ingresso ingressoAtualizado = ingressoService.atualizarIngresso(id, dto.toModel());
        return ResponseEntity.ok(IngressoDtoRes.from(ingressoAtualizado));
    }

    @Operation(summary = "Remover ingresso", description = "Exclui um ingresso")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ingresso removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingresso não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarIngresso(@PathVariable int id) {
        ingressoService.deletarIngresso(id);
        return ResponseEntity.noContent().build();
    }
}
