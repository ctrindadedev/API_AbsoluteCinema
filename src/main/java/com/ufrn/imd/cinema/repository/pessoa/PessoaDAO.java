package com.ufrn.imd.cinema.repository.pessoa;

import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PessoaDAO {

    private final DataSource dataSource;

    public PessoaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO Pessoa (Cpf, sexo, nascimento, nome, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, pessoa.getCpf());
            stmt.setString(2, pessoa.getSexo());
            stmt.setDate(3, Date.valueOf(pessoa.getNascimento()));
            stmt.setString(4, pessoa.getNome());
            stmt.setString(5, pessoa.getEmail());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Pessoa> buscarPorCpf(long cpf) {
        String sql = "SELECT Cpf, sexo, nascimento, nome, email FROM Pessoa WHERE Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cpf);

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

    public List<Pessoa> buscarTodas() {
        String sql = "SELECT Cpf, sexo, nascimento, nome, email FROM Pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pessoas.add(mapRow(rs));
            }
            return pessoas;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Pessoa pessoa) {
        String sql = "UPDATE Pessoa SET sexo = ?, nascimento = ?, nome = ?, email = ? WHERE Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getSexo());
            stmt.setDate(2, Date.valueOf(pessoa.getNascimento()));
            stmt.setString(3, pessoa.getNome());
            stmt.setString(4, pessoa.getEmail());
            stmt.setLong(5, pessoa.getCpf());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(long cpf) {
        String sql = "DELETE FROM Pessoa WHERE Cpf = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, cpf);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Pessoa mapRow(ResultSet rs) throws SQLException {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(rs.getLong("Cpf"));
        pessoa.setSexo(rs.getString("sexo"));
        pessoa.setNascimento(rs.getDate("nascimento").toLocalDate());
        pessoa.setNome(rs.getString("nome"));
        pessoa.setEmail(rs.getString("email"));
        return pessoa;
    }
}
