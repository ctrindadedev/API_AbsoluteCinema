package com.ufrn.imd.cinema.dtos.sessao;

import com.ufrn.imd.cinema.models.sessao.Sessao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de uma sessão")
public class SessaoDtoReq {

    @Schema(description = "Identificador da sessão", example = "1")
    @Positive(message = "Id da sessão deve ser um número positivo")
    private int idSessao;

    @Schema(description = "Data e hora de início da sessão", example = "2026-07-08T20:00:00")
    @NotNull(message = "Data/hora inicial é obrigatória")
    private LocalDateTime dataHoraInicial;

    @Schema(description = "Data e hora de término da sessão", example = "2026-07-08T22:30:00")
    @NotNull(message = "Data/hora final é obrigatória")
    private LocalDateTime dataHoraFinal;

    @Schema(description = "Tipo de exibição da sessão", example = "3D")
    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 3, message = "Tipo deve ter no máximo 3 caracteres")
    private String tipo;

    @Schema(description = "Valor do ingresso para essa sessão", example = "30.0")
    @Positive(message = "Valor da sessão deve ser positivo")
    private float valorSessao;

    @Schema(description = "Identificador da sala onde a sessão ocorrerá", example = "1")
    @Positive(message = "Id da sala deve ser um número positivo")
    private int salaIdSala;

    @Schema(description = "CPF do administrativo responsável pela sessão", example = "12345678901")
    @Positive(message = "CPF do administrativo deve ser um número positivo")
    private long administrativoCpf;

    @Schema(description = "Identificador do filme exibido na sessão", example = "1")
    @Positive(message = "Id do filme deve ser um número positivo")
    private int filmeIdFilme;

    public Sessao toModel() {
        return new Sessao(idSessao, dataHoraInicial, dataHoraFinal, tipo, valorSessao, salaIdSala, administrativoCpf, filmeIdFilme);
    }
}
