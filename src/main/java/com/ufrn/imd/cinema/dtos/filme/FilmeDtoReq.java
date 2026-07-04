package com.ufrn.imd.cinema.dtos.filme;

import com.ufrn.imd.cinema.models.filme.Filme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FilmeDtoReq {

    @Positive(message = "Id do filme deve ser um número positivo")
    private int idFilme;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 45, message = "Título deve ter no máximo 45 caracteres")
    private String titulo;

    @NotNull(message = "Duração é obrigatória")
    @Positive(message = "Duração deve ser um valor positivo")
    private BigDecimal duracao;

    @NotBlank(message = "Linguagem é obrigatória")
    @Size(max = 10, message = "Linguagem deve ter no máximo 10 caracteres")
    private String linguagem;

    @NotBlank(message = "Sinopse é obrigatória")
    @Size(max = 105, message = "Sinopse deve ter no máximo 105 caracteres")
    private String sinopse;

    public Filme toModel() {
        return new Filme(idFilme, titulo, duracao, linguagem, sinopse);
    }
}
