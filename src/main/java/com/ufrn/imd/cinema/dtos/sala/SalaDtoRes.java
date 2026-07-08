package com.ufrn.imd.cinema.dtos.sala;

import com.ufrn.imd.cinema.models.sala.Sala;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de uma sala")
public class SalaDtoRes {

    @Schema(description = "Identificador da sala", example = "1")
    private int idSala;

    public static SalaDtoRes from(Sala sala) {
        SalaDtoRes dto = new SalaDtoRes();
        dto.setIdSala(sala.getIdSala());
        return dto;
    }
}
