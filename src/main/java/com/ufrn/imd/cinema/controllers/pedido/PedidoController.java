package com.ufrn.imd.cinema.controllers.pedido;

import com.ufrn.imd.cinema.dtos.pedido.PedidoDtoReq;
import com.ufrn.imd.cinema.dtos.pedido.PedidoDtoRes;
import com.ufrn.imd.cinema.exceptions.ApiErro;
import com.ufrn.imd.cinema.models.pedido.Pedido;
import com.ufrn.imd.cinema.services.pedido.PedidoService;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Cadastrar pedido", description = "Registra um novo pedido vinculado a um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PostMapping
    public ResponseEntity<PedidoDtoRes> criarPedido(@Valid @RequestBody PedidoDtoReq dto) {
        Pedido pedido = dto.toModel();
        pedidoService.registrarNovoPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoDtoRes.from(pedido));
    }

    @Operation(summary = "Buscar pedido por id", description = "Retorna os dados de um pedido a partir do seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDtoRes> buscarPorId(@PathVariable int id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(PedidoDtoRes.from(pedido));
    }

    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PedidoDtoRes>> listarTodos() {
        List<PedidoDtoRes> pedidos = pedidoService.listarTodos().stream()
                .map(PedidoDtoRes::from)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Atualizar pedido", description = "Atualiza os dados de um pedido existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ApiErro.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDtoRes> atualizarPedido(@PathVariable int id, @Valid @RequestBody PedidoDtoReq dto) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, dto.toModel());
        return ResponseEntity.ok(PedidoDtoRes.from(pedidoAtualizado));
    }

    @Operation(summary = "Remover pedido", description = "Exclui um pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiErro.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable int id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
