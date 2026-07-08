package com.ufrn.imd.cinema.dtos.cliente;

import com.ufrn.imd.cinema.models.cliente.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para registrar uma pessoa como cliente")
public class ClienteDtoReq {

    @Schema(description = "CPF da pessoa", example = "12345678901")
    @Positive(message = "CPF deve ser um número positivo")
    private long pessoaCpf;

    public Cliente toModel() {
        return new Cliente(pessoaCpf);
    }
}
