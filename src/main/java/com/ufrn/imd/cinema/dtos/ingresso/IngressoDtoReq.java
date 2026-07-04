package com.ufrn.imd.cinema.dtos.ingresso;

import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngressoDtoReq {

    @Positive(message = "Id do ingresso deve ser um número positivo")
    private int idIngresso;

    @Positive(message = "Valor do ingresso deve ser positivo")
    private float valorIngresso;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 12, message = "Tipo deve ter no máximo 12 caracteres")
    private String tipo;

    @Positive(message = "Id do pedido deve ser um número positivo")
    private int pedidoIdPedido;

    @Positive(message = "Id do assento deve ser um número positivo")
    private int assentoIdAssento;

    @Positive(message = "Id da sessão deve ser um número positivo")
    private int sessaoIdSessao;

    public Ingresso toModel() {
        return new Ingresso(idIngresso, valorIngresso, tipo, pedidoIdPedido, assentoIdAssento, sessaoIdSessao);
    }
}
