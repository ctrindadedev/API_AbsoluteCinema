package com.ufrn.imd.cinema.controllers.endereco;

import com.ufrn.imd.cinema.dtos.endereco.EnderecoDtoReq;
import com.ufrn.imd.cinema.dtos.endereco.EnderecoDtoRes;
import com.ufrn.imd.cinema.models.endereco.Endereco;
import com.ufrn.imd.cinema.services.endereco.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public ResponseEntity<EnderecoDtoRes> criarEndereco(@Valid @RequestBody EnderecoDtoReq dto) {
        Endereco endereco = dto.toModel();
        enderecoService.registrarNovoEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnderecoDtoRes.from(endereco));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDtoRes> buscarPorId(@PathVariable int id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(EnderecoDtoRes.from(endereco));
    }

    @GetMapping
    public ResponseEntity<List<EnderecoDtoRes>> listarTodos() {
        List<EnderecoDtoRes> enderecos = enderecoService.listarTodos().stream()
                .map(EnderecoDtoRes::from)
                .toList();
        return ResponseEntity.ok(enderecos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDtoRes> atualizarEndereco(@PathVariable int id, @Valid @RequestBody EnderecoDtoReq dto) {
        Endereco enderecoAtualizado = enderecoService.atualizarEndereco(id, dto.toModel());
        return ResponseEntity.ok(EnderecoDtoRes.from(enderecoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable int id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
