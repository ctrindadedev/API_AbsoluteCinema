package com.ufrn.imd.cinema.services.administrativo;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.administrativo.Administrativo;
import com.ufrn.imd.cinema.repository.administrativo.AdministrativoDAO;
import com.ufrn.imd.cinema.repository.funcionario.FuncionarioDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministrativoService {

    private final AdministrativoDAO administrativoDAO;
    private final FuncionarioDAO funcionarioDAO;

    public AdministrativoService(AdministrativoDAO administrativoDAO, FuncionarioDAO funcionarioDAO) {
        this.administrativoDAO = administrativoDAO;
        this.funcionarioDAO = funcionarioDAO;
    }

    public void registrarNovoAdministrativo(Administrativo administrativo) {
        funcionarioDAO.buscarPorCpf(administrativo.getFuncionarioCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Funcionário com CPF " + administrativo.getFuncionarioCpf() + " não encontrado."));

        if (administrativoDAO.buscarPorCpf(administrativo.getFuncionarioCpf()).isPresent()) {
            throw new IllegalArgumentException("Esse funcionário já é administrativo.");
        }

        administrativoDAO.salvar(administrativo);
    }

    public Administrativo buscarPorCpf(long funcionarioCpf) {
        return administrativoDAO.buscarPorCpf(funcionarioCpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrativo com CPF " + funcionarioCpf + " não encontrado."));
    }

    public List<Administrativo> listarTodos() {
        return administrativoDAO.buscarTodos();
    }

    public void deletarAdministrativo(long funcionarioCpf) {
        buscarPorCpf(funcionarioCpf);
        administrativoDAO.deletar(funcionarioCpf);
    }
}
