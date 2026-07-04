package com.ufrn.imd.cinema.repository.pessoa;

import com.ufrn.imd.cinema.models.pessoa.Pessoa;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

@Repository
public class PessoaDAO {

    private final DataSource dataSource;

    public PessoaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Pessoa pessoa) {
        String sql = "INSERT INTO Pessoa (Cpf, sexo, nascimento, nome, email) VALUES (?, ?, ?, ?, ?)";

        // Pegando a conexão do pool do Spring e criando o PreparedStatement
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pessoa.getCpf());
            stmt.setString(2, pessoa.getSexo());
            stmt.setDate(3, Date.valueOf(pessoa.getNascimento()));
            stmt.setString(4, pessoa.getNome());
            stmt.setString(5, pessoa.getEmail());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }
}
