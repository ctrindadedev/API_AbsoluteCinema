package com.ufrn.imd.cinema.dtos.departamento;

import com.ufrn.imd.cinema.models.departamento.Departamento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartamentoDtoRes {

    private String nome;
    private long administrativoCpf;

    public static DepartamentoDtoRes from(Departamento departamento) {
        DepartamentoDtoRes dto = new DepartamentoDtoRes();
        dto.setNome(departamento.getNome());
        dto.setAdministrativoCpf(departamento.getAdministrativoCpf());
        return dto;
    }
}
