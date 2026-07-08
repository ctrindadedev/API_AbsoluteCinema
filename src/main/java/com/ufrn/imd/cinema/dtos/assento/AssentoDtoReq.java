package com.ufrn.imd.cinema.dtos.assento;

import com.ufrn.imd.cinema.models.assento.Assento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de um assento")
public class AssentoDtoReq {

    @Schema(description = "Identificador do assento", example = "1")
    @Positive(message = "Id do assento deve ser um número positivo")
    private int idAssento;

    @Schema(description = "Fila do assento", example = "A")
    private char fila;

    @Schema(description = "Coluna do assento", example = "12")
    @NotBlank(message = "Coluna é obrigatória")
    @Size(max = 2, message = "Coluna deve ter no máximo 2 caracteres")
    private String coluna;

    @Schema(description = "Tipo de prioridade do assento", example = "Comum")
    @NotBlank(message = "Prioritário é obrigatório")
    @Size(max = 10, message = "Prioritário deve ter no máximo 10 caracteres")
    private String prioritario;

    @Schema(description = "Identificador da sala à qual o assento pertence", example = "1")
    @Positive(message = "Id da sala deve ser um número positivo")
    private int salaIdSala;

    public Assento toModel() {
        return new Assento(idAssento, fila, coluna, prioritario, salaIdSala);
    }
}
