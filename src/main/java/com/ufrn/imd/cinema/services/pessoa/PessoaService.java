package com.ufrn.imd.cinema.services.pessoa;


import com.ufrn.imd.cinema.repository.pessoa.PessoaDAO;
import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    private final PessoaDAO pessoaDAO;

    public PessoaService(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public void registrarNovaPessoa(Pessoa pessoa) {
        // Exemplo de regra de negócio
        if (pessoa.getCpf() <= 0) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        pessoaDAO.salvar(pessoa);
    }
}