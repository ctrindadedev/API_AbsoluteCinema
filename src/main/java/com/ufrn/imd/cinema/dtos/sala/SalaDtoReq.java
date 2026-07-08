package com.ufrn.imd.cinema.dtos.sala;

import com.ufrn.imd.cinema.models.sala.Sala;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação de uma sala")
public class SalaDtoReq {

    @Schema(description = "Identificador da sala", example = "1")
    @Positive(message = "Id da sala deve ser um número positivo")
    private int idSala;

    public Sala toModel() {
        return new Sala(idSala);
    }
}
