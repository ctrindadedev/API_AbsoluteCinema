package com.ufrn.imd.cinema.services.departamento;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.departamento.Departamento;
import com.ufrn.imd.cinema.repository.administrativo.AdministrativoDAO;
import com.ufrn.imd.cinema.repository.departamento.DepartamentoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoDAO departamentoDAO;
    private final AdministrativoDAO administrativoDAO;

    public DepartamentoService(DepartamentoDAO departamentoDAO, AdministrativoDAO administrativoDAO) {
        this.departamentoDAO = departamentoDAO;
        this.administrativoDAO = administrativoDAO;
    }

    public void registrarNovoDepartamento(Departamento departamento) {
        administrativoDAO.buscarPorCpf(departamento.getAdministrativoCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Administrativo com CPF " + departamento.getAdministrativoCpf() + " não encontrado."));

        if (departamentoDAO.buscarPorChave(departamento.getNome(), departamento.getAdministrativoCpf()).isPresent()) {
            throw new IllegalArgumentException("Esse departamento já possui esse administrativo cadastrado.");
        }

        departamentoDAO.salvar(departamento);
    }

    public Departamento buscarPorChave(String nome, long administrativoCpf) {
        return departamentoDAO.buscarPorChave(nome, administrativoCpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Departamento '" + nome + "' com administrativo " + administrativoCpf + " não encontrado."));
    }

    public List<Departamento> listarTodos() {
        return departamentoDAO.buscarTodos();
    }

    public void deletarDepartamento(String nome, long administrativoCpf) {
        buscarPorChave(nome, administrativoCpf);
        departamentoDAO.deletar(nome, administrativoCpf);
    }
}
