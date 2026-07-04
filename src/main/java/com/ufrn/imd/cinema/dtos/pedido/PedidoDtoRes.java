package com.ufrn.imd.cinema.dtos.pedido;

import com.ufrn.imd.cinema.models.pedido.Pedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PedidoDtoRes {

    private int idPedido;
    private LocalDate data;
    private LocalTime hora;
    private double valorPago;
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
