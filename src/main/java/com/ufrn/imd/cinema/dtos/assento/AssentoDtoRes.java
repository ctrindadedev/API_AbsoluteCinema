package com.ufrn.imd.cinema.dtos.assento;

import com.ufrn.imd.cinema.models.assento.Assento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um assento")
public class AssentoDtoRes {

    @Schema(description = "Identificador do assento", example = "1")
    private int idAssento;
    @Schema(description = "Fila do assento", example = "A")
    private char fila;
    @Schema(description = "Coluna do assento", example = "12")
    private String coluna;
    @Schema(description = "Tipo de prioridade do assento", example = "Comum")
    private String prioritario;
    @Schema(description = "Identificador da sala à qual o assento pertence", example = "1")
    private int salaIdSala;

    public static AssentoDtoRes from(Assento assento) {
        AssentoDtoRes dto = new AssentoDtoRes();
        dto.setIdAssento(assento.getIdAssento());
        dto.setFila(assento.getFila());
        dto.setColuna(assento.getColuna());
        dto.setPrioritario(assento.getPrioritario());
        dto.setSalaIdSala(assento.getSalaIdSala());
        return dto;
    }
}
