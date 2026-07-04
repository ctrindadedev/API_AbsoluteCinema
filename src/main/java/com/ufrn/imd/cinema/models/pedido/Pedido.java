package com.ufrn.imd.cinema.models.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private int idPedido;
    private LocalDate data;
    private LocalTime hora;
    private double valorPago;
    private long clienteCpf;
}
