package com.ufrn.imd.cinema.services.sala;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.sala.Sala;
import com.ufrn.imd.cinema.repository.sala.SalaDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaDAO salaDAO;

    public SalaService(SalaDAO salaDAO) {
        this.salaDAO = salaDAO;
    }

    public void registrarNovaSala(Sala sala) {
        if (salaDAO.buscarPorId(sala.getIdSala()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma sala cadastrada com esse id.");
        }
        salaDAO.salvar(sala);
    }

    public Sala buscarPorId(int idSala) {
        return salaDAO.buscarPorId(idSala)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala com id " + idSala + " não encontrada."));
    }

    public List<Sala> listarTodas() {
        return salaDAO.buscarTodas();
    }

    public void deletarSala(int idSala) {
        buscarPorId(idSala);
        salaDAO.deletar(idSala);
    }
}
