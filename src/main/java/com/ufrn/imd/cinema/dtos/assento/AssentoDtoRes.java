package com.ufrn.imd.cinema.dtos.assento;

import com.ufrn.imd.cinema.models.assento.Assento;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssentoDtoRes {

    private int idAssento;
    private char fila;
    private String coluna;
    private String prioritario;
    private int salaIdSala;

    public static AssentoDtoRes from(Assento assento) {
        AssentoDtoRes dto = new AssentoDtoRes();
        dto.setIdAssento(assento.getIdAssento());
        dto.setFila(assento.getFila());
        dto.setColuna(assento.getColuna());
        dto.setPrioritario(assento.getPrioritario());
        dto.setSalaIdSala(assento.getSalaIdSala());
        return dto;
    }
}
