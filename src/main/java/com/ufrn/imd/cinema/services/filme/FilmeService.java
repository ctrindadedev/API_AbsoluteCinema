package com.ufrn.imd.cinema.services.filme;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.filme.Filme;
import com.ufrn.imd.cinema.repository.filme.FilmeDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    private final FilmeDAO filmeDAO;

    public FilmeService(FilmeDAO filmeDAO) {
        this.filmeDAO = filmeDAO;
    }

    public void registrarNovoFilme(Filme filme) {
        if (filmeDAO.buscarPorId(filme.getIdFilme()).isPresent()) {
            throw new IllegalArgumentException("Já existe um filme cadastrado com esse id.");
        }
        filmeDAO.salvar(filme);
    }

    public Filme buscarPorId(int idFilme) {
        return filmeDAO.buscarPorId(idFilme)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Filme com id " + idFilme + " não encontrado."));
    }

    public List<Filme> listarTodos() {
        return filmeDAO.buscarTodos();
    }

    public Filme atualizarFilme(int idFilme, Filme dadosAtualizados) {
        buscarPorId(idFilme);
        dadosAtualizados.setIdFilme(idFilme);
        filmeDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarFilme(int idFilme) {
        buscarPorId(idFilme);
        filmeDAO.deletar(idFilme);
    }
}
