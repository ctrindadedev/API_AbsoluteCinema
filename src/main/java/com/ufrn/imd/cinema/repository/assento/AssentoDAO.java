package com.ufrn.imd.cinema.repository.assento;

import com.ufrn.imd.cinema.models.assento.Assento;
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
public class AssentoDAO {

    private final DataSource dataSource;

    public AssentoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Assento assento) {
        String sql = "INSERT INTO Assento (Id_Assento, fila, coluna, prioritario, Sala_Id_Sala) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, assento.getIdAssento());
            stmt.setString(2, String.valueOf(assento.getFila()));
            stmt.setString(3, assento.getColuna());
            stmt.setString(4, assento.getPrioritario());
            stmt.setInt(5, assento.getSalaIdSala());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Assento> buscarPorId(int idAssento) {
        String sql = "SELECT Id_Assento, fila, coluna, prioritario, Sala_Id_Sala FROM Assento WHERE Id_Assento = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAssento);

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

    public List<Assento> buscarTodos() {
        String sql = "SELECT Id_Assento, fila, coluna, prioritario, Sala_Id_Sala FROM Assento";
        List<Assento> assentos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                assentos.add(mapRow(rs));
            }
            return assentos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Assento assento) {
        String sql = "UPDATE Assento SET fila = ?, coluna = ?, prioritario = ?, Sala_Id_Sala = ? WHERE Id_Assento = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(assento.getFila()));
            stmt.setString(2, assento.getColuna());
            stmt.setString(3, assento.getPrioritario());
            stmt.setInt(4, assento.getSalaIdSala());
            stmt.setInt(5, assento.getIdAssento());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int idAssento) {
        String sql = "DELETE FROM Assento WHERE Id_Assento = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAssento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Assento mapRow(ResultSet rs) throws SQLException {
        Assento assento = new Assento();
        assento.setIdAssento(rs.getInt("Id_Assento"));
        assento.setFila(rs.getString("fila").charAt(0));
        assento.setColuna(rs.getString("coluna"));
        assento.setPrioritario(rs.getString("prioritario"));
        assento.setSalaIdSala(rs.getInt("Sala_Id_Sala"));
        return assento;
    }
}
