package com.ufrn.imd.cinema.dtos.funcionario;

import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDtoRes {

    private long pessoaCpf;
    private String departamentoNome;
    private int idFuncionario;

    public static FuncionarioDtoRes from(Funcionario funcionario) {
        FuncionarioDtoRes dto = new FuncionarioDtoRes();
        dto.setPessoaCpf(funcionario.getPessoaCpf());
        dto.setDepartamentoNome(funcionario.getDepartamentoNome());
        dto.setIdFuncionario(funcionario.getIdFuncionario());
        return dto;
    }
}
