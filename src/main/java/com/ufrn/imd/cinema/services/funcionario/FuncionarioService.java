package com.ufrn.imd.cinema.services.funcionario;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.funcionario.Funcionario;
import com.ufrn.imd.cinema.repository.funcionario.FuncionarioDAO;
import com.ufrn.imd.cinema.repository.pessoa.PessoaDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO;
    private final PessoaDAO pessoaDAO;

    public FuncionarioService(FuncionarioDAO funcionarioDAO, PessoaDAO pessoaDAO) {
        this.funcionarioDAO = funcionarioDAO;
        this.pessoaDAO = pessoaDAO;
    }

    public void registrarNovoFuncionario(Funcionario funcionario) {
        pessoaDAO.buscarPorCpf(funcionario.getPessoaCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Pessoa com CPF " + funcionario.getPessoaCpf() + " não encontrada."));

        if (funcionarioDAO.buscarPorCpf(funcionario.getPessoaCpf()).isPresent()) {
            throw new IllegalArgumentException("Essa pessoa já é funcionário.");
        }

        funcionarioDAO.salvar(funcionario);
    }

    public Funcionario buscarPorCpf(long pessoaCpf) {
        return funcionarioDAO.buscarPorCpf(pessoaCpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário com CPF " + pessoaCpf + " não encontrado."));
    }

    public List<Funcionario> listarTodos() {
        return funcionarioDAO.buscarTodos();
    }

    public Funcionario atualizarFuncionario(long pessoaCpf, Funcionario dadosAtualizados) {
        buscarPorCpf(pessoaCpf);
        dadosAtualizados.setPessoaCpf(pessoaCpf);
        funcionarioDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarFuncionario(long pessoaCpf) {
        buscarPorCpf(pessoaCpf);
        funcionarioDAO.deletar(pessoaCpf);
    }
}
