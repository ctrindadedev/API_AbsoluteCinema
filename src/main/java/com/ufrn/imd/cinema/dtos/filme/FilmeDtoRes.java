package com.ufrn.imd.cinema.dtos.filme;

import com.ufrn.imd.cinema.models.filme.Filme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Dados de um filme")
public class FilmeDtoRes {

    @Schema(description = "Identificador do filme", example = "1")
    private int idFilme;
    @Schema(description = "Título do filme", example = "Interestelar")
    private String titulo;
    @Schema(description = "Duração do filme, em minutos", example = "169")
    private BigDecimal duracao;
    @Schema(description = "Idioma original do filme", example = "Inglês")
    private String linguagem;
    @Schema(description = "Sinopse do filme", example = "Uma equipe de exploradores viaja através de um buraco de minhoca no espaço.")
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
