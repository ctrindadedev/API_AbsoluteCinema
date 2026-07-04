package com.ufrn.imd.cinema.controllers.pessoa;

import com.ufrn.imd.cinema.dtos.pessoa.PessoaDtoReq;
import com.ufrn.imd.cinema.dtos.pessoa.PessoaDtoRes;
import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import com.ufrn.imd.cinema.services.pessoa.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaDtoRes> criarPessoa(@Valid @RequestBody PessoaDtoReq dto) {
        Pessoa pessoa = dto.toModel();
        pessoaService.registrarNovaPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(PessoaDtoRes.from(pessoa));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<PessoaDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Pessoa pessoa = pessoaService.buscarPorCpf(cpf);
        return ResponseEntity.ok(PessoaDtoRes.from(pessoa));
    }

    @GetMapping
    public ResponseEntity<List<PessoaDtoRes>> listarTodas() {
        List<PessoaDtoRes> pessoas = pessoaService.listarTodas().stream()
                .map(PessoaDtoRes::from)
                .toList();
        return ResponseEntity.ok(pessoas);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<PessoaDtoRes> atualizarPessoa(@PathVariable long cpf, @Valid @RequestBody PessoaDtoReq dto) {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(cpf, dto.toModel());
        return ResponseEntity.ok(PessoaDtoRes.from(pessoaAtualizada));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarPessoa(@PathVariable long cpf) {
        pessoaService.deletarPessoa(cpf);
        return ResponseEntity.noContent().build();
    }
}
