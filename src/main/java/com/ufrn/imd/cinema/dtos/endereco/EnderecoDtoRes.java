package com.ufrn.imd.cinema.dtos.endereco;

import com.ufrn.imd.cinema.models.endereco.Endereco;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDtoRes {

    private int idEndereco;
    private int cep;
    private String rua;
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
