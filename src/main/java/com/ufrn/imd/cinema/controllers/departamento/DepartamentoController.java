package com.ufrn.imd.cinema.controllers.departamento;

import com.ufrn.imd.cinema.dtos.departamento.DepartamentoDtoReq;
import com.ufrn.imd.cinema.dtos.departamento.DepartamentoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.departamento.Departamento;
import com.ufrn.imd.cinema.services.departamento.DepartamentoService;
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
@RequestMapping("/api/departamentos")
@Tag(name = "Departamentos", description = "Gerenciamento de departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @Operation(summary = "Cadastrar departamento", description = "Registra um novo departamento vinculado a um administrativo responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Departamento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<DepartamentoDtoRes> criarDepartamento(@Valid @RequestBody DepartamentoDtoReq dto) {
        Departamento departamento = dto.toModel();
        departamentoService.registrarNovoDepartamento(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(DepartamentoDtoRes.from(departamento));
    }

    @Operation(summary = "Buscar departamento", description = "Retorna os dados de um departamento a partir do nome e do CPF do administrativo responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{nome}/{administrativoCpf}")
    public ResponseEntity<DepartamentoDtoRes> buscarPorChave(@PathVariable String nome, @PathVariable long administrativoCpf) {
        Departamento departamento = departamentoService.buscarPorChave(nome, administrativoCpf);
        return ResponseEntity.ok(DepartamentoDtoRes.from(departamento));
    }

    @Operation(summary = "Listar departamentos", description = "Retorna todos os departamentos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de departamentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<DepartamentoDtoRes>> listarTodos() {
        List<DepartamentoDtoRes> departamentos = departamentoService.listarTodos().stream()
                .map(DepartamentoDtoRes::from)
                .toList();
        return ResponseEntity.ok(departamentos);
    }

    @Operation(summary = "Remover departamento", description = "Exclui um departamento a partir do nome e do CPF do administrativo responsável")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{nome}/{administrativoCpf}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable String nome, @PathVariable long administrativoCpf) {
        departamentoService.deletarDepartamento(nome, administrativoCpf);
        return ResponseEntity.noContent().build();
    }
}
