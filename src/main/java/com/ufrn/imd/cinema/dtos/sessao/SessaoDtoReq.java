package com.ufrn.imd.cinema.dtos.sessao;

import com.ufrn.imd.cinema.models.sessao.Sessao;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SessaoDtoReq {

    @Positive(message = "Id da sessão deve ser um número positivo")
    private int idSessao;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Hora inicial é obrigatória")
    private LocalTime horaInicial;

    @NotNull(message = "Hora final é obrigatória")
    private LocalTime horaFinal;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 3, message = "Tipo deve ter no máximo 3 caracteres")
    private String tipo;

    @Positive(message = "Valor da sessão deve ser positivo")
    private float valorSessao;

    @Positive(message = "Id da sala deve ser um número positivo")
    private int salaIdSala;

    @Positive(message = "CPF do administrativo deve ser um número positivo")
    private long administrativoCpf;

    @Positive(message = "Id do filme deve ser um número positivo")
    private int filmeIdFilme;

    public Sessao toModel() {
        return new Sessao(idSessao, data, horaInicial, horaFinal, tipo, valorSessao, salaIdSala, administrativoCpf, filmeIdFilme);
    }
}
