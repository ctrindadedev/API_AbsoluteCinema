package com.ufrn.imd.cinema.dtos.administrativo;

import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para registrar um funcionário como administrativo")
public class AdministrativoDtoReq {

    @Schema(description = "CPF do funcionário", example = "12345678901")
    @Positive(message = "CPF deve ser um número positivo")
    private long funcionarioCpf;

    public Administrativo toModel() {
        return new Administrativo(funcionarioCpf);
    }
}
