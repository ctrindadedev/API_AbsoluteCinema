package com.ufrn.imd.cinema.dtos.ingresso;

import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngressoDtoRes {

    private int idIngresso;
    private float valorIngresso;
    private String tipo;
    private int pedidoIdPedido;
    private int assentoIdAssento;
    private int sessaoIdSessao;

    public static IngressoDtoRes from(Ingresso ingresso) {
        IngressoDtoRes dto = new IngressoDtoRes();
        dto.setIdIngresso(ingresso.getIdIngresso());
        dto.setValorIngresso(ingresso.getValorIngresso());
        dto.setTipo(ingresso.getTipo());
        dto.setPedidoIdPedido(ingresso.getPedidoIdPedido());
        dto.setAssentoIdAssento(ingresso.getAssentoIdAssento());
        dto.setSessaoIdSessao(ingresso.getSessaoIdSessao());
        return dto;
    }
}
