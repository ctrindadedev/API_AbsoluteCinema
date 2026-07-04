package com.ufrn.imd.cinema.repository.pedido;

import com.ufrn.imd.cinema.models.pedido.Pedido;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PedidoDAO {

    private final DataSource dataSource;

    public PedidoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Pedido pedido) {
        String sql = "INSERT INTO Pedido (Id_Pedido, data, hora, valor_pago, Cliente_Cpf) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdPedido());
            stmt.setDate(2, Date.valueOf(pedido.getData()));
            stmt.setTime(3, Time.valueOf(pedido.getHora()));
            stmt.setDouble(4, pedido.getValorPago());
            stmt.setLong(5, pedido.getClienteCpf());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Pedido> buscarPorId(int idPedido) {
        String sql = "SELECT Id_Pedido, data, hora, valor_pago, Cliente_Cpf FROM Pedido WHERE Id_Pedido = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

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

    public List<Pedido> buscarTodos() {
        String sql = "SELECT Id_Pedido, data, hora, valor_pago, Cliente_Cpf FROM Pedido";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(mapRow(rs));
            }
            return pedidos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Pedido pedido) {
        String sql = "UPDATE Pedido SET data = ?, hora = ?, valor_pago = ?, Cliente_Cpf = ? WHERE Id_Pedido = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(pedido.getData()));
            stmt.setTime(2, Time.valueOf(pedido.getHora()));
            stmt.setDouble(3, pedido.getValorPago());
            stmt.setLong(4, pedido.getClienteCpf());
            stmt.setInt(5, pedido.getIdPedido());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int idPedido) {
        String sql = "DELETE FROM Pedido WHERE Id_Pedido = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Pedido mapRow(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("Id_Pedido"));
        pedido.setData(rs.getDate("data").toLocalDate());
        pedido.setHora(rs.getTime("hora").toLocalTime());
        pedido.setValorPago(rs.getDouble("valor_pago"));
        pedido.setClienteCpf(rs.getLong("Cliente_Cpf"));
        return pedido;
    }
}
