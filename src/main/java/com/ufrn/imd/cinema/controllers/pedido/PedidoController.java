package com.ufrn.imd.cinema.controllers.pedido;

import com.ufrn.imd.cinema.dtos.pedido.PedidoDtoReq;
import com.ufrn.imd.cinema.dtos.pedido.PedidoDtoRes;
import com.ufrn.imd.cinema.models.pedido.Pedido;
import com.ufrn.imd.cinema.services.pedido.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoDtoRes> criarPedido(@Valid @RequestBody PedidoDtoReq dto) {
        Pedido pedido = dto.toModel();
        pedidoService.registrarNovoPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoDtoRes.from(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDtoRes> buscarPorId(@PathVariable int id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(PedidoDtoRes.from(pedido));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDtoRes>> listarTodos() {
        List<PedidoDtoRes> pedidos = pedidoService.listarTodos().stream()
                .map(PedidoDtoRes::from)
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDtoRes> atualizarPedido(@PathVariable int id, @Valid @RequestBody PedidoDtoReq dto) {
        Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, dto.toModel());
        return ResponseEntity.ok(PedidoDtoRes.from(pedidoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable int id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
