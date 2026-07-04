package com.ufrn.imd.cinema.dtos.pessoa;

import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaDtoReq {

    @Positive(message = "CPF deve ser um número positivo")
    private long cpf;

    @NotBlank(message = "Sexo é obrigatório")
    @Pattern(regexp = "[MF]", message = "Sexo deve ser 'M' ou 'F'")
    private String sexo;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve estar no passado")
    private LocalDate nascimento;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 60, message = "Nome deve ter no máximo 60 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 65, message = "Email deve ter no máximo 65 caracteres")
    private String email;

    public Pessoa toModel() {
        return new Pessoa(cpf, sexo, nascimento, nome, email);
    }
}
