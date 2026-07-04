package com.ufrn.imd.cinema.models.assento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assento {
    private int idAssento;
    private char fila;
    private String coluna;
    private String prioritario;
    private int salaIdSala;
}
