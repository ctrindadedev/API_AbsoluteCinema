package com.ufrn.imd.cinema.dtos.departamento;

import com.ufrn.imd.cinema.models.departamento.Departamento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um departamento")
public class DepartamentoDtoRes {

    @Schema(description = "Nome do departamento", example = "TI")
    private String nome;
    @Schema(description = "CPF do administrativo responsável pelo departamento", example = "12345678901")
    private long administrativoCpf;

    public static DepartamentoDtoRes from(Departamento departamento) {
        DepartamentoDtoRes dto = new DepartamentoDtoRes();
        dto.setNome(departamento.getNome());
        dto.setAdministrativoCpf(departamento.getAdministrativoCpf());
        return dto;
    }
}
