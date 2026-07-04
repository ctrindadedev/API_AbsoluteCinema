package com.ufrn.imd.cinema.controllers.cliente;

import com.ufrn.imd.cinema.dtos.cliente.ClienteDtoReq;
import com.ufrn.imd.cinema.dtos.cliente.ClienteDtoRes;
import com.ufrn.imd.cinema.models.cliente.Cliente;
import com.ufrn.imd.cinema.services.cliente.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteDtoRes> criarCliente(@Valid @RequestBody ClienteDtoReq dto) {
        Cliente cliente = dto.toModel();
        clienteService.registrarNovoCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteDtoRes.from(cliente));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(ClienteDtoRes.from(cliente));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDtoRes>> listarTodos() {
        List<ClienteDtoRes> clientes = clienteService.listarTodos().stream()
                .map(ClienteDtoRes::from)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable long cpf) {
        clienteService.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}
