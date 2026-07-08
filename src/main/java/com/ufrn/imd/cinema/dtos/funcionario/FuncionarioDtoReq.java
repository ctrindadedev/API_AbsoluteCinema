package com.ufrn.imd.cinema.dtos.funcionario;

import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de um funcionário")
public class FuncionarioDtoReq {

    @Schema(description = "CPF da pessoa", example = "12345678901")
    @Positive(message = "CPF deve ser um número positivo")
    private long pessoaCpf;

    @Schema(description = "Nome do departamento ao qual o funcionário pertence", example = "TI")
    @Size(max = 15, message = "Nome do departamento deve ter no máximo 15 caracteres")
    private String departamentoNome;

    @Schema(description = "Identificador do funcionário", example = "1")
    @Positive(message = "Id do funcionário deve ser um número positivo")
    private int idFuncionario;

    public Funcionario toModel() {
        return new Funcionario(pessoaCpf, departamentoNome, idFuncionario);
    }
}
