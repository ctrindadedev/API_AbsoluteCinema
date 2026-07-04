package com.ufrn.imd.cinema.models.funcionario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {
    private long pessoaCpf;
    private String departamentoNome;
    private int idFuncionario;
}
