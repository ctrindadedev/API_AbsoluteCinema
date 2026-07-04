package com.ufrn.imd.cinema.services.pedido;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.pedido.Pedido;
import com.ufrn.imd.cinema.repository.cliente.ClienteDAO;
import com.ufrn.imd.cinema.repository.pedido.PedidoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ClienteDAO clienteDAO;

    public PedidoService(PedidoDAO pedidoDAO, ClienteDAO clienteDAO) {
        this.pedidoDAO = pedidoDAO;
        this.clienteDAO = clienteDAO;
    }

    public void registrarNovoPedido(Pedido pedido) {
        clienteDAO.buscarPorCpf(pedido.getClienteCpf())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com CPF " + pedido.getClienteCpf() + " não encontrado."));

        if (pedidoDAO.buscarPorId(pedido.getIdPedido()).isPresent()) {
            throw new IllegalArgumentException("Já existe um pedido cadastrado com esse id.");
        }

        pedidoDAO.salvar(pedido);
    }

    public Pedido buscarPorId(int idPedido) {
        return pedidoDAO.buscarPorId(idPedido)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido com id " + idPedido + " não encontrado."));
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.buscarTodos();
    }

    public Pedido atualizarPedido(int idPedido, Pedido dadosAtualizados) {
        buscarPorId(idPedido);
        dadosAtualizados.setIdPedido(idPedido);
        pedidoDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarPedido(int idPedido) {
        buscarPorId(idPedido);
        pedidoDAO.deletar(idPedido);
    }
}
