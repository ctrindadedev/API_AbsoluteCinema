package com.ufrn.imd.cinema.controllers.assento;

import com.ufrn.imd.cinema.dtos.assento.AssentoDtoReq;
import com.ufrn.imd.cinema.dtos.assento.AssentoDtoRes;
import com.ufrn.imd.cinema.models.assento.Assento;
import com.ufrn.imd.cinema.services.assento.AssentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assentos")
public class AssentoController {

    private final AssentoService assentoService;

    public AssentoController(AssentoService assentoService) {
        this.assentoService = assentoService;
    }

    @PostMapping
    public ResponseEntity<AssentoDtoRes> criarAssento(@Valid @RequestBody AssentoDtoReq dto) {
        Assento assento = dto.toModel();
        assentoService.registrarNovoAssento(assento);
        return ResponseEntity.status(HttpStatus.CREATED).body(AssentoDtoRes.from(assento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssentoDtoRes> buscarPorId(@PathVariable int id) {
        Assento assento = assentoService.buscarPorId(id);
        return ResponseEntity.ok(AssentoDtoRes.from(assento));
    }

    @GetMapping
    public ResponseEntity<List<AssentoDtoRes>> listarTodos() {
        List<AssentoDtoRes> assentos = assentoService.listarTodos().stream()
                .map(AssentoDtoRes::from)
                .toList();
        return ResponseEntity.ok(assentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssentoDtoRes> atualizarAssento(@PathVariable int id, @Valid @RequestBody AssentoDtoReq dto) {
        Assento assentoAtualizado = assentoService.atualizarAssento(id, dto.toModel());
        return ResponseEntity.ok(AssentoDtoRes.from(assentoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAssento(@PathVariable int id) {
        assentoService.deletarAssento(id);
        return ResponseEntity.noContent().build();
    }
}
