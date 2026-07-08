package com.ufrn.imd.cinema.controllers.filme;

import com.ufrn.imd.cinema.dtos.filme.FilmeDtoReq;
import com.ufrn.imd.cinema.dtos.filme.FilmeDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.filme.Filme;
import com.ufrn.imd.cinema.services.filme.FilmeService;
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
@RequestMapping("/api/filmes")
@Tag(name = "Filmes", description = "Gerenciamento do catálogo de filmes")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @Operation(summary = "Cadastrar filme", description = "Registra um novo filme no catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Filme criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<FilmeDtoRes> criarFilme(@Valid @RequestBody FilmeDtoReq dto) {
        Filme filme = dto.toModel();
        filmeService.registrarNovoFilme(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(FilmeDtoRes.from(filme));
    }

    @Operation(summary = "Buscar filme por id", description = "Retorna os dados de um filme a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme encontrado"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FilmeDtoRes> buscarPorId(@PathVariable int id) {
        Filme filme = filmeService.buscarPorId(id);
        return ResponseEntity.ok(FilmeDtoRes.from(filme));
    }

    @Operation(summary = "Listar filmes", description = "Retorna todos os filmes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de filmes retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<FilmeDtoRes>> listarTodos() {
        List<FilmeDtoRes> filmes = filmeService.listarTodos().stream()
                .map(FilmeDtoRes::from)
                .toList();
        return ResponseEntity.ok(filmes);
    }

    @Operation(summary = "Atualizar filme", description = "Atualiza os dados de um filme existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<FilmeDtoRes> atualizarFilme(@PathVariable int id, @Valid @RequestBody FilmeDtoReq dto) {
        Filme filmeAtualizado = filmeService.atualizarFilme(id, dto.toModel());
        return ResponseEntity.ok(FilmeDtoRes.from(filmeAtualizado));
    }

    @Operation(summary = "Remover filme", description = "Exclui um filme do catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Filme removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable int id) {
        filmeService.deletarFilme(id);
        return ResponseEntity.noContent().build();
    }
}
