package com.ufrn.imd.cinema.models.pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {
    private int cpf;
    private String sexo;
    private LocalDate nascimento;
    private String nome;
    private String email;

}