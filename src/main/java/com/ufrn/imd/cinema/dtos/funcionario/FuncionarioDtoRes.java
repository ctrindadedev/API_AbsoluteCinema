package com.ufrn.imd.cinema.dtos.funcionario;

import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um funcionário")
public class FuncionarioDtoRes {

    @Schema(description = "CPF da pessoa", example = "12345678901")
    private long pessoaCpf;
    @Schema(description = "Nome do departamento ao qual o funcionário pertence", example = "TI")
    private String departamentoNome;
    @Schema(description = "Identificador do funcionário", example = "1")
    private int idFuncionario;

    public static FuncionarioDtoRes from(Funcionario funcionario) {
        FuncionarioDtoRes dto = new FuncionarioDtoRes();
        dto.setPessoaCpf(funcionario.getPessoaCpf());
        dto.setDepartamentoNome(funcionario.getDepartamentoNome());
        dto.setIdFuncionario(funcionario.getIdFuncionario());
        return dto;
    }
}
