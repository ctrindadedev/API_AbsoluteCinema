package com.ufrn.imd.cinema.dtos.pessoa;

import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PessoaDtoRes {

    private int cpf;
    private String sexo;
    private LocalDate nascimento;
    private String nome;
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
