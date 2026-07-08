package com.ufrn.imd.cinema.dtos.pedido;

import com.ufrn.imd.cinema.models.pedido.Pedido;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Schema(description = "Dados de um pedido")
public class PedidoDtoRes {

    @Schema(description = "Identificador do pedido", example = "1")
    private int idPedido;
    @Schema(description = "Data do pedido", example = "2026-07-08")
    private LocalDate data;
    @Schema(description = "Hora do pedido", example = "20:30:00")
    private LocalTime hora;
    @Schema(description = "Valor total pago no pedido", example = "50.0")
    private double valorPago;
    @Schema(description = "CPF do cliente que realizou o pedido", example = "12345678901")
    private long clienteCpf;

    public static PedidoDtoRes from(Pedido pedido) {
        PedidoDtoRes dto = new PedidoDtoRes();
        dto.setIdPedido(pedido.getIdPedido());
        dto.setData(pedido.getData());
        dto.setHora(pedido.getHora());
        dto.setValorPago(pedido.getValorPago());
        dto.setClienteCpf(pedido.getClienteCpf());
        return dto;
    }
}
