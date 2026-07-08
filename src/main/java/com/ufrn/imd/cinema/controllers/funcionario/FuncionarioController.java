package com.ufrn.imd.cinema.controllers.funcionario;

import com.ufrn.imd.cinema.dtos.funcionario.FuncionarioDtoReq;
import com.ufrn.imd.cinema.dtos.funcionario.FuncionarioDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import com.ufrn.imd.cinema.services.funcionario.FuncionarioService;
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
@RequestMapping("/api/funcionarios")
@Tag(name = "Funcionários", description = "Gerenciamento de funcionários")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(summary = "Cadastrar funcionário", description = "Registra uma pessoa como funcionário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<FuncionarioDtoRes> criarFuncionario(@Valid @RequestBody FuncionarioDtoReq dto) {
        Funcionario funcionario = dto.toModel();
        funcionarioService.registrarNovoFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(FuncionarioDtoRes.from(funcionario));
    }

    @Operation(summary = "Buscar funcionário por CPF", description = "Retorna os dados de um funcionário a partir do CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<FuncionarioDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(FuncionarioDtoRes.from(funcionario));
    }

    @Operation(summary = "Listar funcionários", description = "Retorna todos os funcionários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de funcionários retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<FuncionarioDtoRes>> listarTodos() {
        List<FuncionarioDtoRes> funcionarios = funcionarioService.listarTodos().stream()
                .map(FuncionarioDtoRes::from)
                .toList();
        return ResponseEntity.ok(funcionarios);
    }

    @Operation(summary = "Atualizar funcionário", description = "Atualiza os dados de um funcionário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{cpf}")
    public ResponseEntity<FuncionarioDtoRes> atualizarFuncionario(@PathVariable long cpf, @Valid @RequestBody FuncionarioDtoReq dto) {
        Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(cpf, dto.toModel());
        return ResponseEntity.ok(FuncionarioDtoRes.from(funcionarioAtualizado));
    }

    @Operation(summary = "Remover funcionário", description = "Exclui um funcionário a partir do CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Funcionário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable long cpf) {
        funcionarioService.deletarFuncionario(cpf);
        return ResponseEntity.noContent().build();
    }
}
