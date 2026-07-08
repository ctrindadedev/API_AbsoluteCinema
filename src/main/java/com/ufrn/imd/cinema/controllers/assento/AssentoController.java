package com.ufrn.imd.cinema.controllers.assento;

import com.ufrn.imd.cinema.dtos.assento.AssentoDtoReq;
import com.ufrn.imd.cinema.dtos.assento.AssentoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.assento.Assento;
import com.ufrn.imd.cinema.services.assento.AssentoService;
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
@RequestMapping("/api/assentos")
@Tag(name = "Assentos", description = "Gerenciamento de assentos das salas")
public class AssentoController {

    private final AssentoService assentoService;

    public AssentoController(AssentoService assentoService) {
        this.assentoService = assentoService;
    }

    @Operation(summary = "Cadastrar assento", description = "Registra um novo assento em uma sala")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Assento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<AssentoDtoRes> criarAssento(@Valid @RequestBody AssentoDtoReq dto) {
        Assento assento = dto.toModel();
        assentoService.registrarNovoAssento(assento);
        return ResponseEntity.status(HttpStatus.CREATED).body(AssentoDtoRes.from(assento));
    }

    @Operation(summary = "Buscar assento por id", description = "Retorna os dados de um assento a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assento encontrado"),
            @ApiResponse(responseCode = "404", description = "Assento não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AssentoDtoRes> buscarPorId(@PathVariable int id) {
        Assento assento = assentoService.buscarPorId(id);
        return ResponseEntity.ok(AssentoDtoRes.from(assento));
    }

    @Operation(summary = "Listar assentos", description = "Retorna todos os assentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de assentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AssentoDtoRes>> listarTodos() {
        List<AssentoDtoRes> assentos = assentoService.listarTodos().stream()
                .map(AssentoDtoRes::from)
                .toList();
        return ResponseEntity.ok(assentos);
    }

    @Operation(summary = "Atualizar assento", description = "Atualiza os dados de um assento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Assento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Assento não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AssentoDtoRes> atualizarAssento(@PathVariable int id, @Valid @RequestBody AssentoDtoReq dto) {
        Assento assentoAtualizado = assentoService.atualizarAssento(id, dto.toModel());
        return ResponseEntity.ok(AssentoDtoRes.from(assentoAtualizado));
    }

    @Operation(summary = "Remover assento", description = "Exclui um assento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Assento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Assento não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssento(@PathVariable int id) {
        assentoService.deletarAssento(id);
        return ResponseEntity.noContent().build();
    }
}
