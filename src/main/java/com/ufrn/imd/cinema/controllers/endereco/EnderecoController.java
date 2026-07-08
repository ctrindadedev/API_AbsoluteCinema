package com.ufrn.imd.cinema.controllers.endereco;

import com.ufrn.imd.cinema.dtos.endereco.EnderecoDtoReq;
import com.ufrn.imd.cinema.dtos.endereco.EnderecoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.endereco.Endereco;
import com.ufrn.imd.cinema.services.endereco.EnderecoService;
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
@RequestMapping("/api/enderecos")
@Tag(name = "Endereços", description = "Gerenciamento de endereços")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @Operation(summary = "Cadastrar endereço", description = "Registra um novo endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<EnderecoDtoRes> criarEndereco(@Valid @RequestBody EnderecoDtoReq dto) {
        Endereco endereco = dto.toModel();
        enderecoService.registrarNovoEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnderecoDtoRes.from(endereco));
    }

    @Operation(summary = "Buscar endereço por id", description = "Retorna os dados de um endereço a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDtoRes> buscarPorId(@PathVariable int id) {
        Endereco endereco = enderecoService.buscarPorId(id);
        return ResponseEntity.ok(EnderecoDtoRes.from(endereco));
    }

    @Operation(summary = "Listar endereços", description = "Retorna todos os endereços cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EnderecoDtoRes>> listarTodos() {
        List<EnderecoDtoRes> enderecos = enderecoService.listarTodos().stream()
                .map(EnderecoDtoRes::from)
                .toList();
        return ResponseEntity.ok(enderecos);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDtoRes> atualizarEndereco(@PathVariable int id, @Valid @RequestBody EnderecoDtoReq dto) {
        Endereco enderecoAtualizado = enderecoService.atualizarEndereco(id, dto.toModel());
        return ResponseEntity.ok(EnderecoDtoRes.from(enderecoAtualizado));
    }

    @Operation(summary = "Remover endereço", description = "Exclui um endereço")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEndereco(@PathVariable int id) {
        enderecoService.deletarEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
