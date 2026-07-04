package com.ufrn.imd.cinema.dtos.pedido;

import com.ufrn.imd.cinema.models.pedido.Pedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PedidoDtoReq {

    @Positive(message = "Id do pedido deve ser um número positivo")
    private int idPedido;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Hora é obrigatória")
    private LocalTime hora;

    @PositiveOrZero(message = "Valor pago deve ser zero ou positivo")
    private double valorPago;

    @Positive(message = "CPF do cliente deve ser um número positivo")
    private long clienteCpf;

    public Pedido toModel() {
        return new Pedido(idPedido, data, hora, valorPago, clienteCpf);
    }
}
