package com.ufrn.imd.cinema.dtos.ingresso;

import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de um ingresso")
public class IngressoDtoReq {

    @Schema(description = "Identificador do ingresso", example = "1")
    @Positive(message = "Id do ingresso deve ser um número positivo")
    private int idIngresso;

    @Schema(description = "Valor do ingresso", example = "25.0")
    @Positive(message = "Valor do ingresso deve ser positivo")
    private float valorIngresso;

    @Schema(description = "Tipo do ingresso", example = "Inteira")
    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 12, message = "Tipo deve ter no máximo 12 caracteres")
    private String tipo;

    @Schema(description = "Identificador do pedido vinculado", example = "1")
    @Positive(message = "Id do pedido deve ser um número positivo")
    private int pedidoIdPedido;

    @Schema(description = "Identificador do assento vinculado", example = "1")
    @Positive(message = "Id do assento deve ser um número positivo")
    private int assentoIdAssento;

    @Schema(description = "Identificador da sessão vinculada", example = "1")
    @Positive(message = "Id da sessão deve ser um número positivo")
    private int sessaoIdSessao;

    public Ingresso toModel() {
        return new Ingresso(idIngresso, valorIngresso, tipo, pedidoIdPedido, assentoIdAssento, sessaoIdSessao);
    }
}
