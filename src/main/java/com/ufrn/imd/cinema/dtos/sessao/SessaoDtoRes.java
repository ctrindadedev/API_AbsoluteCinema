package com.ufrn.imd.cinema.dtos.sessao;

import com.ufrn.imd.cinema.models.sessao.Sessao;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Dados de uma sessão")
public class SessaoDtoRes {

    @Schema(description = "Identificador da sessão", example = "1")
    private int idSessao;
    @Schema(description = "Data e hora de início da sessão", example = "2026-07-08T20:00:00")
    private LocalDateTime dataHoraInicial;
    @Schema(description = "Data e hora de término da sessão", example = "2026-07-08T22:30:00")
    private LocalDateTime dataHoraFinal;
    @Schema(description = "Tipo de exibição da sessão", example = "3D")
    private String tipo;
    @Schema(description = "Valor do ingresso para essa sessão", example = "30.0")
    private float valorSessao;
    @Schema(description = "Identificador da sala onde a sessão ocorrerá", example = "1")
    private int salaIdSala;
    @Schema(description = "CPF do administrativo responsável pela sessão", example = "12345678901")
    private long administrativoCpf;
    @Schema(description = "Identificador do filme exibido na sessão", example = "1")
    private int filmeIdFilme;

    public static SessaoDtoRes from(Sessao sessao) {
        SessaoDtoRes dto = new SessaoDtoRes();
        dto.setIdSessao(sessao.getIdSessao());
        dto.setDataHoraInicial(sessao.getDataHoraInicial());
        dto.setDataHoraFinal(sessao.getDataHoraFinal());
        dto.setTipo(sessao.getTipo());
        dto.setValorSessao(sessao.getValorSessao());
        dto.setSalaIdSala(sessao.getSalaIdSala());
        dto.setAdministrativoCpf(sessao.getAdministrativoCpf());
        dto.setFilmeIdFilme(sessao.getFilmeIdFilme());
        return dto;
    }
}
