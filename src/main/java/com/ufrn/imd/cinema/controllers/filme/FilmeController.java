package com.ufrn.imd.cinema.controllers.filme;

import com.ufrn.imd.cinema.dtos.filme.FilmeDtoReq;
import com.ufrn.imd.cinema.dtos.filme.FilmeDtoRes;
import com.ufrn.imd.cinema.models.filme.Filme;
import com.ufrn.imd.cinema.services.filme.FilmeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @PostMapping
    public ResponseEntity<FilmeDtoRes> criarFilme(@Valid @RequestBody FilmeDtoReq dto) {
        Filme filme = dto.toModel();
        filmeService.registrarNovoFilme(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(FilmeDtoRes.from(filme));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeDtoRes> buscarPorId(@PathVariable int id) {
        Filme filme = filmeService.buscarPorId(id);
        return ResponseEntity.ok(FilmeDtoRes.from(filme));
    }

    @GetMapping
    public ResponseEntity<List<FilmeDtoRes>> listarTodos() {
        List<FilmeDtoRes> filmes = filmeService.listarTodos().stream()
                .map(FilmeDtoRes::from)
                .toList();
        return ResponseEntity.ok(filmes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmeDtoRes> atualizarFilme(@PathVariable int id, @Valid @RequestBody FilmeDtoReq dto) {
        Filme filmeAtualizado = filmeService.atualizarFilme(id, dto.toModel());
        return ResponseEntity.ok(FilmeDtoRes.from(filmeAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable int id) {
        filmeService.deletarFilme(id);
        return ResponseEntity.noContent().build();
    }
}
