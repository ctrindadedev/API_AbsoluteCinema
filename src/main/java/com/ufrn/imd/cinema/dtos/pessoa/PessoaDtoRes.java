package com.ufrn.imd.cinema.dtos.pessoa;

import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Schema(description = "Dados de uma pessoa")
public class PessoaDtoRes {

    @Schema(description = "CPF da pessoa", example = "12345678901")
    private long cpf;
    @Schema(description = "Sexo da pessoa ('M' ou 'F')", example = "M")
    private String sexo;
    @Schema(description = "Data de nascimento", example = "1995-04-23")
    private LocalDate nascimento;
    @Schema(description = "Nome completo", example = "João da Silva")
    private String nome;
    @Schema(description = "Email de contato", example = "joao.silva@email.com")
    private String email;

    public static PessoaDtoRes from(Pessoa pessoa) {
        PessoaDtoRes dto = new PessoaDtoRes();
        dto.setCpf(pessoa.getCpf());
        dto.setSexo(pessoa.getSexo());
        dto.setNascimento(pessoa.getNascimento());
        dto.setNome(pessoa.getNome());
        dto.setEmail(pessoa.getEmail());
        return dto;
    }
}
