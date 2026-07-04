package com.ufrn.imd.cinema.services.cliente;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.cliente.Cliente;
import com.ufrn.imd.cinema.repository.cliente.ClienteDAO;
import com.ufrn.imd.cinema.repository.pessoa.PessoaDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteDAO clienteDAO;
    private final PessoaDAO pessoaDAO;

    public ClienteService(ClienteDAO clienteDAO, PessoaDAO pessoaDAO) {
        this.clienteDAO = clienteDAO;
        this.pessoaDAO = pessoaDAO;
    }

    public void registrarNovoCliente(Cliente cliente) {
        pessoaDAO.buscarPorCpf(cliente.getPessoaCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Pessoa com CPF " + cliente.getPessoaCpf() + " não encontrada."));

        if (clienteDAO.buscarPorCpf(cliente.getPessoaCpf()).isPresent()) {
            throw new IllegalArgumentException("Essa pessoa já é cliente.");
        }

        clienteDAO.salvar(cliente);
    }

    public Cliente buscarPorCpf(long pessoaCpf) {
        return clienteDAO.buscarPorCpf(pessoaCpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com CPF " + pessoaCpf + " não encontrado."));
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.buscarTodos();
    }

    public void deletarCliente(long pessoaCpf) {
        buscarPorCpf(pessoaCpf);
        clienteDAO.deletar(pessoaCpf);
    }
}
