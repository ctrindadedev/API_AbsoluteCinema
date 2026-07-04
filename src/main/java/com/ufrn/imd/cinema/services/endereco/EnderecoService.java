package com.ufrn.imd.cinema.services.endereco;

import com.ufrn.imd.cinema.exceptions.RecursoNaoEncontradoException;
import com.ufrn.imd.cinema.models.endereco.Endereco;
import com.ufrn.imd.cinema.repository.endereco.EnderecoDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoService {

    private final EnderecoDAO enderecoDAO;

    public EnderecoService(EnderecoDAO enderecoDAO) {
        this.enderecoDAO = enderecoDAO;
    }

    public void registrarNovoEndereco(Endereco endereco) {
        if (enderecoDAO.buscarPorId(endereco.getIdEndereco()).isPresent()) {
            throw new IllegalArgumentException("Já existe um endereço cadastrado com esse id.");
        }
        enderecoDAO.salvar(endereco);
    }

    public Endereco buscarPorId(int idEndereco) {
        return enderecoDAO.buscarPorId(idEndereco)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço com id " + idEndereco + " não encontrado."));
    }

    public List<Endereco> listarTodos() {
        return enderecoDAO.buscarTodos();
    }

    public Endereco atualizarEndereco(int idEndereco, Endereco dadosAtualizados) {
        buscarPorId(idEndereco);
        dadosAtualizados.setIdEndereco(idEndereco);
        enderecoDAO.atualizar(dadosAtualizados);
        return dadosAtualizados;
    }

    public void deletarEndereco(int idEndereco) {
        buscarPorId(idEndereco);
        enderecoDAO.deletar(idEndereco);
    }
}
