package com.ufrn.imd.cinema.repository.ingresso;

import com.ufrn.imd.cinema.models.ingresso.Ingresso;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngressoDAO {

    private final DataSource dataSource;

    public IngressoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Ingresso ingresso) {
        String sql = "INSERT INTO Ingresso (Id_Ingresso, valor_ingresso, tipo, Pedido_Id_Pedido, Assento_Id_Assento, Sessao_Id_Sessao) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingresso.getIdIngresso());
            stmt.setFloat(2, ingresso.getValorIngresso());
            stmt.setString(3, ingresso.getTipo());
            stmt.setInt(4, ingresso.getPedidoIdPedido());
            stmt.setInt(5, ingresso.getAssentoIdAssento());
            stmt.setInt(6, ingresso.getSessaoIdSessao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Ingresso> buscarPorId(int idIngresso) {
        String sql = "SELECT Id_Ingresso, valor_ingresso, tipo, Pedido_Id_Pedido, Assento_Id_Assento, Sessao_Id_Sessao "
                + "FROM Ingresso WHERE Id_Ingresso = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public List<Ingresso> buscarTodos() {
        String sql = "SELECT Id_Ingresso, valor_ingresso, tipo, Pedido_Id_Pedido, Assento_Id_Assento, Sessao_Id_Sessao FROM Ingresso";
        List<Ingresso> ingressos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ingressos.add(mapRow(rs));
            }
            return ingressos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Ingresso ingresso) {
        String sql = "UPDATE Ingresso SET valor_ingresso = ?, tipo = ?, Pedido_Id_Pedido = ?, Assento_Id_Assento = ?, Sessao_Id_Sessao = ? "
                + "WHERE Id_Ingresso = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setFloat(1, ingresso.getValorIngresso());
            stmt.setString(2, ingresso.getTipo());
            stmt.setInt(3, ingresso.getPedidoIdPedido());
            stmt.setInt(4, ingresso.getAssentoIdAssento());
            stmt.setInt(5, ingresso.getSessaoIdSessao());
            stmt.setInt(6, ingresso.getIdIngresso());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int idIngresso) {
        String sql = "DELETE FROM Ingresso WHERE Id_Ingresso = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Ingresso mapRow(ResultSet rs) throws SQLException {
        Ingresso ingresso = new Ingresso();
        ingresso.setIdIngresso(rs.getInt("Id_Ingresso"));
        ingresso.setValorIngresso(rs.getFloat("valor_ingresso"));
        ingresso.setTipo(rs.getString("tipo"));
        ingresso.setPedidoIdPedido(rs.getInt("Pedido_Id_Pedido"));
        ingresso.setAssentoIdAssento(rs.getInt("Assento_Id_Assento"));
        ingresso.setSessaoIdSessao(rs.getInt("Sessao_Id_Sessao"));
        return ingresso;
    }
}
