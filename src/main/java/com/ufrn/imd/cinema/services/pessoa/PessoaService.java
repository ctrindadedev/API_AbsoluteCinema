package com.ufrn.imd.cinema.services.pessoa;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import com.ufrn.imd.cinema.repository.pessoa.PessoaDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaDAO pessoaDAO;

    public PessoaService(PessoaDAO pessoaDAO) {
        this.pessoaDAO = pessoaDAO;
    }

    public void registrarNovaPessoa(Pessoa pessoa) {
        if (pessoa.getCpf() <= 0) {
            throw new IllegalArgumentException("CPF inválido.");
        }

        if (pessoaDAO.buscarPorCpf(pessoa.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma pessoa cadastrada com esse CPF.");
        }

        pessoaDAO.salvar(pessoa);
    }

    public Pessoa buscarPorCpf(int cpf) {
        return pessoaDAO.buscarPorCpf(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pessoa com CPF " + cpf + " não encontrada."));
    }

    public List<Pessoa> listarTodas() {
        return pessoaDAO.buscarTodas();
    }

    public Pessoa atualizarPessoa(int cpf, Pessoa dadosAtualizados) {
        buscarPorCpf(cpf);
        dadosAtualizados.setCpf(cpf);
        pessoaDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarPessoa(int cpf) {
        buscarPorCpf(cpf);
        pessoaDAO.deletar(cpf);
    }
}
