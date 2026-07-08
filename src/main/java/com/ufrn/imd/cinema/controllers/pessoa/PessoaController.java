package com.ufrn.imd.cinema.controllers.pessoa;

import com.ufrn.imd.cinema.dtos.pessoa.PessoaDtoReq;
import com.ufrn.imd.cinema.dtos.pessoa.PessoaDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import com.ufrn.imd.cinema.services.pessoa.PessoaService;
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
@RequestMapping("/api/pessoas")
@Tag(name = "Pessoas", description = "Gerenciamento de pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Cadastrar pessoa", description = "Registra uma nova pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<PessoaDtoRes> criarPessoa(@Valid @RequestBody PessoaDtoReq dto) {
        Pessoa pessoa = dto.toModel();
        pessoaService.registrarNovaPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(PessoaDtoRes.from(pessoa));
    }

    @Operation(summary = "Buscar pessoa por CPF", description = "Retorna os dados de uma pessoa a partir do CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<PessoaDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        return ResponseEntity.ok(PessoaDtoRes.from(pessoa));
    }

    @Operation(summary = "Listar pessoas", description = "Retorna todas as pessoas cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PessoaDtoRes>> listarTodas() {
        List<PessoaDtoRes> pessoas = pessoaService.listarTodas().stream()
                .map(PessoaDtoRes::from)
                .toList();
        return ResponseEntity.ok(pessoas);
    }

    @Operation(summary = "Atualizar pessoa", description = "Atualiza os dados de uma pessoa existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{cpf}")
    public ResponseEntity<PessoaDtoRes> atualizarPessoa(@PathVariable long cpf, @Valid @RequestBody PessoaDtoReq dto) {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(cpf, dto.toModel());
        return ResponseEntity.ok(PessoaDtoRes.from(pessoaAtualizada));
    }

    @Operation(summary = "Remover pessoa", description = "Exclui uma pessoa a partir do CPF")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable long cpf) {
        pessoaService.deletarPessoa(cpf);
        return ResponseEntity.noContent().build();
    }
}
