package com.ufrn.imd.cinema.services.assento;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.assento.Assento;
import com.ufrn.imd.cinema.repository.assento.AssentoDAO;
import com.ufrn.imd.cinema.repository.sala.SalaDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssentoService {

    private final AssentoDAO assentoDAO;
    private final SalaDAO salaDAO;

    public AssentoService(AssentoDAO assentoDAO, SalaDAO salaDAO) {
        this.assentoDAO = assentoDAO;
        this.salaDAO = salaDAO;
    }

    public void registrarNovoAssento(Assento assento) {
        salaDAO.buscarPorId(assento.getSalaIdSala())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala com id " + assento.getSalaIdSala() + " não encontrada."));

        if (assentoDAO.buscarPorId(assento.getIdAssento()).isPresent()) {
            throw new IllegalArgumentException("Já existe um assento cadastrado com esse id.");
        }

        assentoDAO.salvar(assento);
    }

    public Assento buscarPorId(int idAssento) {
        return assentoDAO.buscarPorId(idAssento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Assento com id " + idAssento + " não encontrado."));
    }

    public List<Assento> listarTodos() {
        return assentoDAO.buscarTodos();
    }

    public Assento atualizarAssento(int idAssento, Assento dadosAtualizados) {
        buscarPorId(idAssento);
        dadosAtualizados.setIdAssento(idAssento);
        assentoDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarAssento(int idAssento) {
        buscarPorId(idAssento);
        assentoDAO.deletar(idAssento);
    }
}
