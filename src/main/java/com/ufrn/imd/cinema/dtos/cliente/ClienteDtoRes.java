package com.ufrn.imd.cinema.dtos.cliente;

import com.ufrn.imd.cinema.models.cliente.Cliente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDtoRes {

    private long pessoaCpf;

    public static ClienteDtoRes from(Cliente cliente) {
        ClienteDtoRes dto = new ClienteDtoRes();
        dto.setPessoaCpf(cliente.getPessoaCpf());
        return dto;
    }
}
