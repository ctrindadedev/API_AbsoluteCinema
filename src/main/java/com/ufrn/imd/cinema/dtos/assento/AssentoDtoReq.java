package com.ufrn.imd.cinema.dtos.assento;

import com.ufrn.imd.cinema.models.assento.Assento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssentoDtoReq {

    @Positive(message = "Id do assento deve ser um número positivo")
    private int idAssento;

    private char fila;

    @NotBlank(message = "Coluna é obrigatória")
    @Size(max = 2, message = "Coluna deve ter no máximo 2 caracteres")
    private String coluna;

    @NotBlank(message = "Prioritário é obrigatório")
    @Size(max = 10, message = "Prioritário deve ter no máximo 10 caracteres")
    private String prioritario;

    @Positive(message = "Id da sala deve ser um número positivo")
    private int salaIdSala;

    public Assento toModel() {
        return new Assento(idAssento, fila, coluna, prioritario, salaIdSala);
    }
}
