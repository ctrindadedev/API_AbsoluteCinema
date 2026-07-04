package com.ufrn.imd.cinema.dtos.sala;

import com.ufrn.imd.cinema.models.sala.Sala;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaDtoReq {

    @Positive(message = "Id da sala deve ser um número positivo")
    private int idSala;

    public Sala toModel() {
        return new Sala(idSala);
    }
}
