package com.ufrn.imd.cinema.dtos.cliente;

import com.ufrn.imd.cinema.models.cliente.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um cliente")
public class ClienteDtoRes {

    @Schema(description = "CPF da pessoa", example = "12345678901")
    private long pessoaCpf;

    public static ClienteDtoRes from(Cliente cliente) {
        ClienteDtoRes dto = new ClienteDtoRes();
        dto.setPessoaCpf(cliente.getPessoaCpf());
        return dto;
    }
}
