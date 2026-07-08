package com.ufrn.imd.cinema.dtos.pedido;

import com.ufrn.imd.cinema.models.pedido.Pedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de um pedido")
public class PedidoDtoReq {

    @Schema(description = "Identificador do pedido", example = "1")
    @Positive(message = "Id do pedido deve ser um número positivo")
    private int idPedido;

    @Schema(description = "Data do pedido", example = "2026-07-08")
    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @Schema(description = "Hora do pedido", example = "20:30:00")
    @NotNull(message = "Hora é obrigatória")
    private LocalTime hora;

    @Schema(description = "Valor total pago no pedido", example = "50.0")
    @PositiveOrZero(message = "Valor pago deve ser zero ou positivo")
    private double valorPago;

    @Schema(description = "CPF do cliente que realizou o pedido", example = "12345678901")
    @Positive(message = "CPF do cliente deve ser um número positivo")
    private long clienteCpf;

    public Pedido toModel() {
        return new Pedido(idPedido, data, hora, valorPago, clienteCpf);
    }
}
