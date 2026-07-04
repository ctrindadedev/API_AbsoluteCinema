package com.ufrn.imd.cinema.models.ingresso;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingresso {
    private int idIngresso;
    private float valorIngresso;
    private String tipo;
    private int pedidoIdPedido;
    private int assentoIdAssento;
    private int sessaoIdSessao;
}
