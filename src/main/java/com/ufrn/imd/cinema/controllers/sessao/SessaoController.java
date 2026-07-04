package com.ufrn.imd.cinema.controllers.sessao;

import com.ufrn.imd.cinema.dtos.sessao.SessaoDtoReq;
import com.ufrn.imd.cinema.dtos.sessao.SessaoDtoRes;
import com.ufrn.imd.cinema.models.sessao.Sessao;
import com.ufrn.imd.cinema.services.sessao.SessaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping
    public ResponseEntity<SessaoDtoRes> criarSessao(@Valid @RequestBody SessaoDtoReq dto) {
        Sessao sessao = dto.toModel();
        sessaoService.registrarNovaSessao(sessao);
        return ResponseEntity.status(HttpStatus.CREATED).body(SessaoDtoRes.from(sessao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessaoDtoRes> buscarPorId(@PathVariable int id) {
        Sessao sessao = sessaoService.buscarPorId(id);
        return ResponseEntity.ok(SessaoDtoRes.from(sessao));
    }

    @GetMapping
    public ResponseEntity<List<SessaoDtoRes>> listarTodas() {
        List<SessaoDtoRes> sessoes = sessaoService.listarTodas().stream()
                .map(SessaoDtoRes::from)
                .toList();
        return ResponseEntity.ok(sessoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessaoDtoRes> atualizarSessao(@PathVariable int id, @Valid @RequestBody SessaoDtoReq dto) {
        Sessao sessaoAtualizada = sessaoService.atualizarSessao(id, dto.toModel());
        return ResponseEntity.ok(SessaoDtoRes.from(sessaoAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSessao(@PathVariable int id) {
        sessaoService.deletarSessao(id);
        return ResponseEntity.noContent().build();
    }
}
