package com.ufrn.imd.cinema.models.sessao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sessao {
    private int idSessao;
    private LocalDateTime dataHoraInicial;
    private LocalDateTime dataHoraFinal;
    private String tipo;
    private float valorSessao;
    private int salaIdSala;
    private long administrativoCpf;
    private int filmeIdFilme;
}
