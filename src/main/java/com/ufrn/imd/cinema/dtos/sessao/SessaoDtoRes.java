package com.ufrn.imd.cinema.dtos.sessao;

import com.ufrn.imd.cinema.models.sessao.Sessao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SessaoDtoRes {

    private int idSessao;
    private LocalDate data;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private String tipo;
    private float valorSessao;
    private int salaIdSala;
    private long administrativoCpf;
    private int filmeIdFilme;

    public static SessaoDtoRes from(Sessao sessao) {
        SessaoDtoRes dto = new SessaoDtoRes();
        dto.setIdSessao(sessao.getIdSessao());
        dto.setData(sessao.getData());
        dto.setHoraInicial(sessao.getHoraInicial());
        dto.setHoraFinal(sessao.getHoraFinal());
        dto.setTipo(sessao.getTipo());
        dto.setValorSessao(sessao.getValorSessao());
        dto.setSalaIdSala(sessao.getSalaIdSala());
        dto.setAdministrativoCpf(sessao.getAdministrativoCpf());
        dto.setFilmeIdFilme(sessao.getFilmeIdFilme());
        return dto;
    }
}
