package com.ufrn.imd.cinema.dtos.administrativo;

import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministrativoDtoReq {

    @Positive(message = "CPF deve ser um número positivo")
    private long funcionarioCpf;

    public Administrativo toModel() {
        return new Administrativo(funcionarioCpf);
    }
}
