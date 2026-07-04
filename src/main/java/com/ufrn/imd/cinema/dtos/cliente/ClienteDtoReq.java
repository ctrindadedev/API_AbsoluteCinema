package com.ufrn.imd.cinema.dtos.cliente;

import com.ufrn.imd.cinema.models.cliente.Cliente;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDtoReq {

    @Positive(message = "CPF deve ser um número positivo")
    private long pessoaCpf;

    public Cliente toModel() {
        return new Cliente(pessoaCpf);
    }
}
