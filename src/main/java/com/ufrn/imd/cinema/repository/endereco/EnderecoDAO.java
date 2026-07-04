package com.ufrn.imd.cinema.repository.endereco;

import com.ufrn.imd.cinema.models.endereco.Endereco;
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
public class EnderecoDAO {

    private final DataSource dataSource;

    public EnderecoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Endereco endereco) {
        String sql = "INSERT INTO Endereco (Id_endereco, cep, rua, numero) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, endereco.getIdEndereco());
            stmt.setInt(2, endereco.getCep());
            stmt.setString(3, endereco.getRua());
            stmt.setInt(4, endereco.getNumero());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Endereco> buscarPorId(int idEndereco) {
        String sql = "SELECT Id_endereco, cep, rua, numero FROM Endereco WHERE Id_endereco = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEndereco);

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

    public List<Endereco> buscarTodos() {
        String sql = "SELECT Id_endereco, cep, rua, numero FROM Endereco";
        List<Endereco> enderecos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                enderecos.add(mapRow(rs));
            }
            return enderecos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Endereco endereco) {
        String sql = "UPDATE Endereco SET cep = ?, rua = ?, numero = ? WHERE Id_endereco = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, endereco.getCep());
            stmt.setString(2, endereco.getRua());
            stmt.setInt(3, endereco.getNumero());
            stmt.setInt(4, endereco.getIdEndereco());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int idEndereco) {
        String sql = "DELETE FROM Endereco WHERE Id_endereco = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEndereco);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Endereco mapRow(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setIdEndereco(rs.getInt("Id_endereco"));
        endereco.setCep(rs.getInt("cep"));
        endereco.setRua(rs.getString("rua"));
        endereco.setNumero(rs.getInt("numero"));
        return endereco;
    }
}
