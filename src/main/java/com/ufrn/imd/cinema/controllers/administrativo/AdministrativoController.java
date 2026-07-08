package com.ufrn.imd.cinema.controllers.administrativo;

import com.ufrn.imd.cinema.dtos.administrativo.AdministrativoDtoReq;
import com.ufrn.imd.cinema.dtos.administrativo.AdministrativoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import com.ufrn.imd.cinema.services.administrativo.AdministrativoService;
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
@RequestMapping("/api/administrativos")
@Tag(name = "Administrativos", description = "Gerenciamento de funcionários administrativos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @Operation(summary = "Cadastrar administrativo", description = "Registra um funcionário como administrativo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Administrativo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<AdministrativoDtoRes> criarAdministrativo(@Valid @RequestBody AdministrativoDtoReq dto) {
        Administrativo administrativo = dto.toModel();
        administrativoService.registrarNovoAdministrativo(administrativo);
        return ResponseEntity.status(HttpStatus.CREATED).body(AdministrativoDtoRes.from(administrativo));
    }

    @Operation(summary = "Buscar administrativo por CPF", description = "Retorna os dados de um administrativo a partir do CPF do funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Administrativo encontrado"),
            @ApiResponse(responseCode = "404", description = "Administrativo não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<AdministrativoDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Administrativo administrativo = administrativoService.buscarPorCpf(cpf);
        return ResponseEntity.ok(AdministrativoDtoRes.from(administrativo));
    }

    @Operation(summary = "Listar administrativos", description = "Retorna todos os administrativos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de administrativos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<AdministrativoDtoRes>> listarTodos() {
        List<AdministrativoDtoRes> administrativos = administrativoService.listarTodos().stream()
                .map(AdministrativoDtoRes::from)
                .toList();
        return ResponseEntity.ok(administrativos);
    }

    @Operation(summary = "Remover administrativo", description = "Exclui um administrativo a partir do CPF do funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Administrativo removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrativo não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarAdministrativo(@PathVariable long cpf) {
        administrativoService.deletarAdministrativo(cpf);
        return ResponseEntity.noContent().build();
    }
}
