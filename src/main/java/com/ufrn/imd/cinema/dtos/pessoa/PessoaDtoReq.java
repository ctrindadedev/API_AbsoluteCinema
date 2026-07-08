package com.ufrn.imd.cinema.dtos.pessoa;

import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de uma pessoa")
public class PessoaDtoReq {

    @Schema(description = "CPF da pessoa", example = "12345678901")
    @Positive(message = "CPF deve ser um número positivo")
    private long cpf;

    @Schema(description = "Sexo da pessoa ('M' ou 'F')", example = "M")
    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "[MF]", message = "Sexo deve ser 'M' ou 'F'")
    private String sexo;

    @Schema(description = "Data de nascimento", example = "1995-04-23")
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate nascimento;

    @Schema(description = "Nome completo", example = "João da Silva")
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 60, message = "Nome deve ter no máximo 60 caracteres")
    private String nome;

    @Schema(description = "Email de contato", example = "joao.silva@email.com")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 65, message = "Email deve ter no máximo 65 caracteres")
    private String email;

    public Pessoa toModel() {
        return new Pessoa(cpf, sexo, nascimento, nome, email);
    }
}
