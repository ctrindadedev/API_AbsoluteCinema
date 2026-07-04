package com.ufrn.imd.cinema.dtos.administrativo;

import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministrativoDtoRes {

    private long funcionarioCpf;

    public static AdministrativoDtoRes from(Administrativo administrativo) {
        AdministrativoDtoRes dto = new AdministrativoDtoRes();
        dto.setFuncionarioCpf(administrativo.getFuncionarioCpf());
        return dto;
    }
}
