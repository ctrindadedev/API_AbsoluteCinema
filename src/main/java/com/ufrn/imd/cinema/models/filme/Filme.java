package com.ufrn.imd.cinema.models.filme;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filme {
    private int idFilme;
    private String titulo;
    private BigDecimal duracao;
    private String linguagem;
    private String sinopse;
}
