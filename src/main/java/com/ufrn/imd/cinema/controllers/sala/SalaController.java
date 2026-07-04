package com.ufrn.imd.cinema.controllers.sala;

import com.ufrn.imd.cinema.dtos.sala.SalaDtoReq;
import com.ufrn.imd.cinema.dtos.sala.SalaDtoRes;
import com.ufrn.imd.cinema.models.sala.Sala;
import com.ufrn.imd.cinema.services.sala.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaDtoRes> criarSala(@Valid @RequestBody SalaDtoReq dto) {
        Sala sala = dto.toModel();
        salaService.registrarNovaSala(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(SalaDtoRes.from(sala));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDtoRes> buscarPorId(@PathVariable int id) {
        Sala sala = salaService.buscarPorId(id);
        return ResponseEntity.ok(SalaDtoRes.from(sala));
    }

    @GetMapping
    public ResponseEntity<List<SalaDtoRes>> listarTodas() {
        List<SalaDtoRes> salas = salaService.listarTodas().stream()
                .map(SalaDtoRes::from)
                .toList();
        return ResponseEntity.ok(salas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable int id) {
        salaService.deletarSala(id);
        return ResponseEntity.noContent().build();
    }
}
