package com.ufrn.imd.cinema.controllers.funcionario;

import com.ufrn.imd.cinema.dtos.funcionario.FuncionarioDtoReq;
import com.ufrn.imd.cinema.dtos.funcionario.FuncionarioDtoRes;
import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import com.ufrn.imd.cinema.services.funcionario.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<FuncionarioDtoRes> criarFuncionario(@Valid @RequestBody FuncionarioDtoReq dto) {
        Funcionario funcionario = dto.toModel();
        funcionarioService.registrarNovoFuncionario(funcionario);
        return ResponseEntity.status(HttpStatus.CREATED).body(FuncionarioDtoRes.from(funcionario));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<FuncionarioDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(FuncionarioDtoRes.from(funcionario));
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioDtoRes>> listarTodos() {
        List<FuncionarioDtoRes> funcionarios = funcionarioService.listarTodos().stream()
                .map(FuncionarioDtoRes::from)
                .toList();
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<FuncionarioDtoRes> atualizarFuncionario(@PathVariable long cpf, @Valid @RequestBody FuncionarioDtoReq dto) {
        Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(cpf, dto.toModel());
        return ResponseEntity.ok(FuncionarioDtoRes.from(funcionarioAtualizado));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable long cpf) {
        funcionarioService.deletarFuncionario(cpf);
        return ResponseEntity.noContent().build();
    }
}
