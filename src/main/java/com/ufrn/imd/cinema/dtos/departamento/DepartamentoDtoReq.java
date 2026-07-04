package com.ufrn.imd.cinema.dtos.departamento;

import com.ufrn.imd.cinema.models.departamento.Departamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartamentoDtoReq {

    @NotBlank(message = "Nome do departamento é obrigatório")
    @Size(max = 15, message = "Nome do departamento deve ter no máximo 15 caracteres")
    private String nome;

    @Positive(message = "CPF do administrativo deve ser um número positivo")
    private long administrativoCpf;

    public Departamento toModel() {
        return new Departamento(nome, administrativoCpf);
    }
}
