package com.ufrn.imd.cinema.exceptions;

import java.time.Instant;
import java.util.List;

public record ApiErro(Instant timestamp, int status, String erro, String mensagem, List<String> detalhes) {

    public ApiErro(int status, String erro, String mensagem) {
        this(Instant.now(), status, erro, mensagem, List.of());
    }

    public ApiErro(int status, String erro, String mensagem, List<String> detalhes) {
        this(Instant.now(), status, erro, mensagem, detalhes);
    }
}
