package com.ufrn.imd.cinema.controllers.cliente;

import com.ufrn.imd.cinema.dtos.cliente.ClienteDtoReq;
import com.ufrn.imd.cinema.dtos.cliente.ClienteDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.cliente.Cliente;
import com.ufrn.imd.cinema.services.cliente.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Cadastrar cliente", description = "Registra uma pessoa como cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<ClienteDtoRes> criarCliente(@Valid @RequestBody ClienteDtoReq dto) {
        Cliente cliente = dto.toModel();
        clienteService.registrarNovoCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClienteDtoRes.from(cliente));
    }

    @Operation(summary = "Buscar cliente por CPF", description = "Retorna os dados de um cliente a partir do CPF da pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<ClienteDtoRes> buscarPorCpf(@PathVariable long cpf) {
        Cliente cliente = clienteService.buscarPorCpf(cpf);
        return ResponseEntity.ok(ClienteDtoRes.from(cliente));
    }

    @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ClienteDtoRes>> listarTodos() {
        List<ClienteDtoRes> clientes = clienteService.listarTodos().stream()
                .map(ClienteDtoRes::from)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Remover cliente", description = "Exclui um cliente a partir do CPF da pessoa")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable long cpf) {
        clienteService.deletarCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}
