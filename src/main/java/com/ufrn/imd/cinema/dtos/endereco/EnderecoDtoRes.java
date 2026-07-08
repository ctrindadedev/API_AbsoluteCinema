package com.ufrn.imd.cinema.dtos.endereco;

import com.ufrn.imd.cinema.models.endereco.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Dados de um endereço")
public class EnderecoDtoRes {

    @Schema(description = "Identificador do endereço", example = "1")
    private int idEndereco;
    @Schema(description = "CEP do endereço", example = "59000000")
    private int cep;
    @Schema(description = "Nome da rua", example = "Av. Senador Salgado Filho")
    private String rua;
    @Schema(description = "Número do imóvel", example = "1787")
    private int numero;

    public static EnderecoDtoRes from(Endereco endereco) {
        EnderecoDtoRes dto = new EnderecoDtoRes();
        dto.setIdEndereco(endereco.getIdEndereco());
        dto.setCep(endereco.getCep());
        dto.setRua(endereco.getRua());
        dto.setNumero(endereco.getNumero());
        return dto;
    }
}
