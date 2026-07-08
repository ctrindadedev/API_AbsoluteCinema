package com.ufrn.imd.cinema.controllers.sessao;

import com.ufrn.imd.cinema.dtos.sessao.SessaoDtoReq;
import com.ufrn.imd.cinema.dtos.sessao.SessaoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.sessao.Sessao;
import com.ufrn.imd.cinema.services.sessao.SessaoService;
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
@RequestMapping("/api/sessoes")
@Tag(name = "Sessões", description = "Gerenciamento de sessões de exibição")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @Operation(summary = "Cadastrar sessão", description = "Registra uma nova sessão de exibição de um filme")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sessão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<SessaoDtoRes> criarSessao(@Valid @RequestBody SessaoDtoReq dto) {
        Sessao sessao = dto.toModel();
        sessaoService.registrarNovaSessao(sessao);
        return ResponseEntity.status(HttpStatus.CREATED).body(SessaoDtoRes.from(sessao));
    }

    @Operation(summary = "Buscar sessão por id", description = "Retorna os dados de uma sessão a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão encontrada"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SessaoDtoRes> buscarPorId(@PathVariable int id) {
        Sessao sessao = sessaoService.buscarPorId(id);
        return ResponseEntity.ok(SessaoDtoRes.from(sessao));
    }

    @Operation(summary = "Listar sessões", description = "Retorna todas as sessões cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de sessões retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<SessaoDtoRes>> listarTodas() {
        List<SessaoDtoRes> sessoes = sessaoService.listarTodas().stream()
                .map(SessaoDtoRes::from)
                .toList();
        return ResponseEntity.ok(sessoes);
    }

    @Operation(summary = "Atualizar sessão", description = "Atualiza os dados de uma sessão existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SessaoDtoRes> atualizarSessao(@PathVariable int id, @Valid @RequestBody SessaoDtoReq dto) {
        Sessao sessaoAtualizada = sessaoService.atualizarSessao(id, dto.toModel());
        return ResponseEntity.ok(SessaoDtoRes.from(sessaoAtualizada));
    }

    @Operation(summary = "Remover sessão", description = "Exclui uma sessão de exibição")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sessão removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSessao(@PathVariable int id) {
        sessaoService.deletarSessao(id);
        return ResponseEntity.noContent().build();
    }
}
