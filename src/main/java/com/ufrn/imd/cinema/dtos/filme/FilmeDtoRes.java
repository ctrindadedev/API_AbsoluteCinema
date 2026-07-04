package com.ufrn.imd.cinema.dtos.filme;

import com.ufrn.imd.cinema.models.filme.Filme;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FilmeDtoRes {

    private int idFilme;
    private String titulo;
    private BigDecimal duracao;
    private String linguagem;
    private String sinopse;

    public static FilmeDtoRes from(Filme filme) {
        FilmeDtoRes dto = new FilmeDtoRes();
        dto.setIdFilme(filme.getIdFilme());
        dto.setTitulo(filme.getTitulo());
        dto.setDuracao(filme.getDuracao());
        dto.setLinguagem(filme.getLinguagem());
        dto.setSinopse(filme.getSinopse());
        return dto;
    }
}
