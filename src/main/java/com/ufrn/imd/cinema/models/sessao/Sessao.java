package com.ufrn.imd.cinema.models.sessao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {
    private int idSessao;
    private LocalDate data;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private String tipo;
    private float valorSessao;
    private int salaIdSala;
    private long administrativoCpf;
    private int filmeIdFilme;
}
