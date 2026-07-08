package com.ufrn.imd.cinema.dtos.endereco;

import com.ufrn.imd.cinema.models.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados para criação/atualização de um endereço")
public class EnderecoDtoReq {

    @Schema(description = "Identificador do endereço", example = "1")
    @Positive(message = "Id do endereço deve ser um número positivo")
    private int idEndereco;

    @Schema(description = "CEP do endereço", example = "59000000")
    @Positive(message = "CEP deve ser um número positivo")
    private int cep;

    @Schema(description = "Nome da rua", example = "Av. Senador Salgado Filho")
    @NotBlank(message = "Rua é obrigatória")
    @Size(max = 45, message = "Rua deve ter no máximo 45 caracteres")
    private String rua;

    @Schema(description = "Número do imóvel", example = "1787")
    @Positive(message = "Número deve ser um valor positivo")
    private int numero;

    public Endereco toModel() {
        return new Endereco(idEndereco, cep, rua, numero);
    }
}
