package com.ufrn.imd.cinema.dtos.funcionario;

import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioDtoReq {

    @Positive(message = "CPF deve ser um número positivo")
    private long pessoaCpf;

    @Size(max = 15, message = "Nome do departamento deve ter no máximo 15 caracteres")
    private String departamentoNome;

    @Positive(message = "Id do funcionário deve ser um número positivo")
    private int idFuncionario;

    public Funcionario toModel() {
        return new Funcionario(pessoaCpf, departamentoNome, idFuncionario);
    }
}
