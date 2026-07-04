package com.ufrn.imd.cinema.controllers.administrativo;

import com.ufrn.imd.cinema.dtos.administrativo.AdministrativoDtoReq;
import com.ufrn.imd.cinema.dtos.administrativo.AdministrativoDtoRes;
import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import com.ufrn.imd.cinema.services.administrativo.AdministrativoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrativos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @PostMapping
    public ResponseEntity<AdministrativoDtoRes> criarAdministrativo(@Valid @RequestBody AdministrativoDtoReq dto) {
        Administrativo administrativo = dto.toModel();
        administrativoService.registrarNovoAdministrativo(administrativo);
        return ResponseEntity.status(HttpStatus.CREATED).body(AdministrativoDtoRes.from(administrativo));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<AdministrativoDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Administrativo administrativo = administrativoService.buscarPorCpf(cpf);
        return ResponseEntity.ok(AdministrativoDtoRes.from(administrativo));
    }

    @GetMapping
    public ResponseEntity<List<AdministrativoDtoRes>> listarTodos() {
        List<AdministrativoDtoRes> administrativos = administrativoService.listarTodos().stream()
                .map(AdministrativoDtoRes::from)
                .toList();
        return ResponseEntity.ok(administrativos);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarAdministrativo(@PathVariable long cpf) {
        administrativoService.deletarAdministrativo(cpf);
        return ResponseEntity.noContent().build();
    }
}
