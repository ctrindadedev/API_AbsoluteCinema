package com.ufrn.imd.cinema.dtos.administrativo;

import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um administrativo")
public class AdministrativoDtoRes {

    @Schema(description = "CPF do funcionário", example = "12345678901")
    private long funcionarioCpf;

    public static AdministrativoDtoRes from(Administrativo administrativo) {
        AdministrativoDtoRes dto = new AdministrativoDtoRes();
        dto.setFuncionarioCpf(administrativo.getFuncionarioCpf());
        return dto;
    }
}
