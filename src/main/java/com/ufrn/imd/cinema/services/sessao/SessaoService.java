package com.ufrn.imd.cinema.services.sessao;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.sessao.Sessao;
import com.ufrn.imd.cinema.repository.administrativo.AdministrativoDAO;
import com.ufrn.imd.cinema.repository.filme.FilmeDAO;
import com.ufrn.imd.cinema.repository.sala.SalaDAO;
import com.ufrn.imd.cinema.repository.sessao.SessaoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessaoService {

    private final SessaoDAO sessaoDAO;
    private final SalaDAO salaDAO;
    private final AdministrativoDAO administrativoDAO;
    private final FilmeDAO filmeDAO;

    public SessaoService(SessaoDAO sessaoDAO, SalaDAO salaDAO, AdministrativoDAO administrativoDAO, FilmeDAO filmeDAO) {
        this.sessaoDAO = sessaoDAO;
        this.salaDAO = salaDAO;
        this.administrativoDAO = administrativoDAO;
        this.filmeDAO = filmeDAO;
    }

    public void registrarNovaSessao(Sessao sessao) {
        salaDAO.buscarPorId(sessao.getSalaIdSala())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala com id " + sessao.getSalaIdSala() + " não encontrada."));
        administrativoDAO.buscarPorCpf(sessao.getAdministrativoCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Administrativo com CPF " + sessao.getAdministrativoCpf() + " não encontrado."));
        filmeDAO.buscarPorId(sessao.getFilmeIdFilme())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Filme com id " + sessao.getFilmeIdFilme() + " não encontrado."));

        if (sessaoDAO.buscarPorId(sessao.getIdSessao()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma sessão cadastrada com esse id.");
        }

        sessaoDAO.salvar(sessao);
    }

    public Sessao buscarPorId(int idSessao) {
        return sessaoDAO.buscarPorId(idSessao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sessão com id " + idSessao + " não encontrada."));
    }

    public List<Sessao> listarTodas() {
        return sessaoDAO.buscarTodas();
    }

    public Sessao atualizarSessao(int idSessao, Sessao dadosAtualizados) {
        buscarPorId(idSessao);
        dadosAtualizados.setIdSessao(idSessao);
        sessaoDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarSessao(int idSessao) {
        buscarPorId(idSessao);
        sessaoDAO.deletar(idSessao);
    }
}
