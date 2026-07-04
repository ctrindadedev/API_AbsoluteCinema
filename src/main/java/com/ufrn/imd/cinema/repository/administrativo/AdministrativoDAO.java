package com.ufrn.imd.cinema.repository.administrativo;

import com.ufrn.imd.cinema.models.administrativo.Administrativo;
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
public class AdministrativoDAO {

    private final DataSource dataSource;

    public AdministrativoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Administrativo administrativo) {
        String sql = "INSERT INTO Administrativo (Funcionario_Cpf) VALUES (?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, administrativo.getFuncionarioCpf());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Administrativo> buscarPorCpf(long funcionarioCpf) {
        String sql = "SELECT Funcionario_Cpf FROM Administrativo WHERE Funcionario_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, funcionarioCpf);

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

    public List<Administrativo> buscarTodos() {
        String sql = "SELECT Funcionario_Cpf FROM Administrativo";
        List<Administrativo> administrativos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                administrativos.add(mapRow(rs));
            }
            return administrativos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(long funcionarioCpf) {
        String sql = "DELETE FROM Administrativo WHERE Funcionario_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, funcionarioCpf);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Administrativo mapRow(ResultSet rs) throws SQLException {
        Administrativo administrativo = new Administrativo();
        administrativo.setFuncionarioCpf(rs.getLong("Funcionario_Cpf"));
        return administrativo;
    }
}
