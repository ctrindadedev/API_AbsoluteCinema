package com.ufrn.imd.cinema.controllers.ingresso;

import com.ufrn.imd.cinema.dtos.ingresso.IngressoDtoReq;
import com.ufrn.imd.cinema.dtos.ingresso.IngressoDtoRes;
import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import com.ufrn.imd.cinema.services.ingresso.IngressoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @PostMapping
    public ResponseEntity<IngressoDtoRes> criarIngresso(@Valid @RequestBody IngressoDtoReq dto) {
        Ingresso ingresso = dto.toModel();
        ingressoService.registrarNovoIngresso(ingresso);
        return ResponseEntity.status(HttpStatus.CREATED).body(IngressoDtoRes.from(ingresso));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoDtoRes> buscarPorId(@PathVariable int id) {
        Ingresso ingresso = ingressoService.buscarPorId(id);
        return ResponseEntity.ok(IngressoDtoRes.from(ingresso));
    }

    @GetMapping
    public ResponseEntity<List<IngressoDtoRes>> listarTodos() {
        List<IngressoDtoRes> ingressos = ingressoService.listarTodos().stream()
                .map(IngressoDtoRes::from)
                .toList();
        return ResponseEntity.ok(ingressos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngressoDtoRes> atualizarIngresso(@PathVariable int id, @Valid @RequestBody IngressoDtoReq dto) {
        Ingresso ingressoAtualizado = ingressoService.atualizarIngresso(id, dto.toModel());
        return ResponseEntity.ok(IngressoDtoRes.from(ingressoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarIngresso(@PathVariable int id) {
        ingressoService.deletarIngresso(id);
        return ResponseEntity.noContent().build();
    }
}
