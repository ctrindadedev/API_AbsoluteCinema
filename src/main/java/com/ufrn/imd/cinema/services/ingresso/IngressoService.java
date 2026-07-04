package com.ufrn.imd.cinema.services.ingresso;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import com.ufrn.imd.cinema.repository.assento.AssentoDAO;
import com.ufrn.imd.cinema.repository.ingresso.IngressoDAO;
import com.ufrn.imd.cinema.repository.pedido.PedidoDAO;
import com.ufrn.imd.cinema.repository.sessao.SessaoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngressoService {

    private final IngressoDAO ingressoDAO;
    private final PedidoDAO pedidoDAO;
    private final AssentoDAO assentoDAO;
    private final SessaoDAO sessaoDAO;

    public IngressoService(IngressoDAO ingressoDAO, PedidoDAO pedidoDAO, AssentoDAO assentoDAO, SessaoDAO sessaoDAO) {
        this.ingressoDAO = ingressoDAO;
        this.pedidoDAO = pedidoDAO;
        this.assentoDAO = assentoDAO;
        this.sessaoDAO = sessaoDAO;
    }

    public void registrarNovoIngresso(Ingresso ingresso) {
        pedidoDAO.buscarPorId(ingresso.getPedidoIdPedido())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com id " + ingresso.getPedidoIdPedido() + " não encontrado."));
        assentoDAO.buscarPorId(ingresso.getAssentoIdAssento())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Assento com id " + ingresso.getAssentoIdAssento() + " não encontrado."));
        sessaoDAO.buscarPorId(ingresso.getSessaoIdSessao())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sessão com id " + ingresso.getSessaoIdSessao() + " não encontrada."));

        if (ingressoDAO.buscarPorId(ingresso.getIdIngresso()).isPresent()) {
            throw new IllegalArgumentException("Já existe um ingresso cadastrado com esse id.");
        }

        ingressoDAO.salvar(ingresso);
    }

    public Ingresso buscarPorId(int idIngresso) {
        return ingressoDAO.buscarPorId(idIngresso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Ingresso com id " + idIngresso + " não encontrado."));
    }

    public List<Ingresso> listarTodos() {
        return ingressoDAO.buscarTodos();
    }

    public Ingresso atualizarIngresso(int idIngresso, Ingresso dadosAtualizados) {
        buscarPorId(idIngresso);
        dadosAtualizados.setIdIngresso(idIngresso);
        ingressoDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarIngresso(int idIngresso) {
        buscarPorId(idIngresso);
        ingressoDAO.deletar(idIngresso);
    }
}
