package com.ufrn.imd.cinema.dtos.filme;

import com.ufrn.imd.cinema.models.filme.Filme;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de um filme")
public class FilmeDtoReq {

    @Schema(description = "Identificador do filme", example = "1")
    @Positive(message = "Id do filme deve ser um número positivo")
    private int idFilme;

    @Schema(description = "Título do filme", example = "Interestelar")
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 45, message = "Título deve ter no máximo 45 caracteres")
    private String titulo;

    @Schema(description = "Duração do filme, em minutos", example = "169")
    @NotNull(message = "Duração é obrigatória")
    @Positive(message = "Duração deve ser um valor positivo")
    private BigDecimal duracao;

    @Schema(description = "Idioma original do filme", example = "Inglês")
    @NotBlank(message = "Linguagem é obrigatória")
    @Size(max = 10, message = "Linguagem deve ter no máximo 10 caracteres")
    private String linguagem;

    @Schema(description = "Sinopse do filme", example = "Uma equipe de exploradores viaja através de um buraco de minhoca no espaço.")
    @NotBlank(message = "Sinopse é obrigatória")
    @Size(max = 105, message = "Sinopse deve ter no máximo 105 caracteres")
    private String sinopse;

    public Filme toModel() {
        return new Filme(idFilme, titulo, duracao, linguagem, sinopse);
    }
}
