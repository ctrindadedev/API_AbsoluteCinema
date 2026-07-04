package com.ufrn.imd.cinema.repository.funcionario;

import com.ufrn.imd.cinema.models.funcionario.Funcionario;
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
public class FuncionarioDAO {

    private final DataSource dataSource;

    public FuncionarioDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (Pessoa_Cpf, Departamento_Nome, id_funcionario) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, funcionario.getPessoaCpf());
            stmt.setString(2, funcionario.getDepartamentoNome());
            stmt.setInt(3, funcionario.getIdFuncionario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Funcionario> buscarPorCpf(long pessoaCpf) {
        String sql = "SELECT Pessoa_Cpf, Departamento_Nome, id_funcionario FROM Funcionario WHERE Pessoa_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pessoaCpf);

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

    public List<Funcionario> buscarTodos() {
        String sql = "SELECT Pessoa_Cpf, Departamento_Nome, id_funcionario FROM Funcionario";
        List<Funcionario> funcionarios = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                funcionarios.add(mapRow(rs));
            }
            return funcionarios;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET Departamento_Nome = ?, id_funcionario = ? WHERE Pessoa_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getDepartamentoNome());
            stmt.setInt(2, funcionario.getIdFuncionario());
            stmt.setLong(3, funcionario.getPessoaCpf());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(long pessoaCpf) {
        String sql = "DELETE FROM Funcionario WHERE Pessoa_Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pessoaCpf);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Funcionario mapRow(ResultSet rs) throws SQLException {
        Funcionario funcionario = new Funcionario();
        funcionario.setPessoaCpf(rs.getLong("Pessoa_Cpf"));
        funcionario.setDepartamentoNome(rs.getString("Departamento_Nome"));
        funcionario.setIdFuncionario(rs.getInt("id_funcionario"));
        return funcionario;
    }
}
