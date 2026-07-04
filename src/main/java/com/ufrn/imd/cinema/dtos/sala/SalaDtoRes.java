package com.ufrn.imd.cinema.dtos.sala;

import com.ufrn.imd.cinema.models.sala.Sala;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaDtoRes {

    private int idSala;

    public static SalaDtoRes from(Sala sala) {
        SalaDtoRes dto = new SalaDtoRes();
        dto.setIdSala(sala.getIdSala());
        return dto;
    }
}
