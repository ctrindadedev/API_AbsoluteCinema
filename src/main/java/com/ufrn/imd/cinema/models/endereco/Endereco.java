package com.ufrn.imd.cinema.models.endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    private int idEndereco;
    private int cep;
    private String rua;
    private int numero;
}
