package com.ufrn.imd.cinema.dtos.endereco;

import com.ufrn.imd.cinema.models.endereco.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDtoReq {

    @Positive(message = "Id do endereço deve ser um número positivo")
    private int idEndereco;

    @Positive(message = "CEP deve ser um número positivo")
    private int cep;

    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 45, message = "Rua deve ter no máximo 45 caracteres")
    private String rua;

    @Positive(message = "Número deve ser um valor positivo")
    private int numero;

    public Endereco toModel() {
        return new Endereco(idEndereco, cep, rua, numero);
    }
}
