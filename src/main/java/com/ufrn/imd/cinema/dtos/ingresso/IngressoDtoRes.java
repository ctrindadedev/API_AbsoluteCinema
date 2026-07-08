package com.ufrn.imd.cinema.dtos.ingresso;

import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um ingresso")
public class IngressoDtoRes {

    @Schema(description = "Identificador do ingresso", example = "1")
    private int idIngresso;
    @Schema(description = "Valor do ingresso", example = "25.0")
    private float valorIngresso;
    @Schema(description = "Tipo do ingresso", example = "Inteira")
    private String tipo;
    @Schema(description = "Identificador do pedido vinculado", example = "1")
    private int pedidoIdPedido;
    @Schema(description = "Identificador do assento vinculado", example = "1")
    private int assentoIdAssento;
    @Schema(description = "Identificador da sessão vinculada", example = "1")
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
