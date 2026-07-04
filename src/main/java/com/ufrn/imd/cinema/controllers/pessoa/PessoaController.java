package com.ufrn.imd.cinema.controllers.pessoa;


import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import com.ufrn.imd.cinema.services.pessoa.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<String> criarPessoa(@RequestBody Pessoa pessoa) {
        pessoaService.registrarNovaPessoa(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa registrada com sucesso!");
    }
}