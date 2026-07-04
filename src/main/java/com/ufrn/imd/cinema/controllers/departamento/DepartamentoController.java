package com.ufrn.imd.cinema.controllers.departamento;

import com.ufrn.imd.cinema.dtos.departamento.DepartamentoDtoReq;
import com.ufrn.imd.cinema.dtos.departamento.DepartamentoDtoRes;
import com.ufrn.imd.cinema.models.departamento.Departamento;
import com.ufrn.imd.cinema.services.departamento.DepartamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @PostMapping
    public ResponseEntity<DepartamentoDtoRes> criarDepartamento(@Valid @RequestBody DepartamentoDtoReq dto) {
        Departamento departamento = dto.toModel();
        departamentoService.registrarNovoDepartamento(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(DepartamentoDtoRes.from(departamento));
    }

    @GetMapping("/{nome}/{administrativoCpf}")
    public ResponseEntity<DepartamentoDtoRes> buscarPorChave(@PathVariable String nome, @PathVariable long administrativoCpf) {
        Departamento departamento = departamentoService.buscarPorChave(nome, administrativoCpf);
        return ResponseEntity.ok(DepartamentoDtoRes.from(departamento));
    }

    @GetMapping
    public ResponseEntity<List<DepartamentoDtoRes>> listarTodos() {
        List<DepartamentoDtoRes> departamentos = departamentoService.listarTodos().stream()
                .map(DepartamentoDtoRes::from)
                .toList();
        return ResponseEntity.ok(departamentos);
    }

    @DeleteMapping("/{nome}/{administrativoCpf}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable String nome, @PathVariable long administrativoCpf) {
        departamentoService.deletarDepartamento(nome, administrativoCpf);
        return ResponseEntity.noContent().build();
    }
}
