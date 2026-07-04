package com.ufrn.imd.cinema.repository.departamento;

import com.ufrn.imd.cinema.models.departamento.Departamento;
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
public class DepartamentoDAO {

    private final DataSource dataSource;

    public DepartamentoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Departamento departamento) {
        String sql = "INSERT INTO Departamento (Nome, Administrativo_Cpf) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, departamento.getNome());
            stmt.setLong(2, departamento.getAdministrativoCpf());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Departamento> buscarPorChave(String nome, long administrativoCpf) {
        String sql = "SELECT Nome, Administrativo_Cpf FROM Departamento WHERE Nome = ? AND Administrativo_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setLong(2, administrativoCpf);

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

    public List<Departamento> buscarTodos() {
        String sql = "SELECT Nome, Administrativo_Cpf FROM Departamento";
        List<Departamento> departamentos = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departamentos.add(mapRow(rs));
            }
            return departamentos;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(String nome, long administrativoCpf) {
        String sql = "DELETE FROM Departamento WHERE Nome = ? AND Administrativo_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setLong(2, administrativoCpf);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Departamento mapRow(ResultSet rs) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setNome(rs.getString("Nome"));
        departamento.setAdministrativoCpf(rs.getLong("Administrativo_Cpf"));
        return departamento;
    }
}
