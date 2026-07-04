package com.ufrn.imd.cinema.repository.filme;

import com.ufrn.imd.cinema.models.filme.Filme;
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
public class FilmeDAO {

    private final DataSource dataSource;

    public FilmeDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Filme filme) {
        String sql = "INSERT INTO Filme (Id_Filme, titulo, duracao, linguagem, sinopse) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, filme.getIdFilme());
            stmt.setString(2, filme.getTitulo());
            stmt.setBigDecimal(3, filme.getDuracao());
            stmt.setString(4, filme.getLinguagem());
            stmt.setString(5, filme.getSinopse());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Filme> buscarPorId(int idFilme) {
        String sql = "SELECT Id_Filme, titulo, duracao, linguagem, sinopse FROM Filme WHERE Id_Filme = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFilme);

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

    public List<Filme> buscarTodos() {
        String sql = "SELECT Id_Filme, titulo, duracao, linguagem, sinopse FROM Filme";
        List<Filme> filmes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                filmes.add(mapRow(rs));
            }
            return filmes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Filme filme) {
        String sql = "UPDATE Filme SET titulo = ?, duracao = ?, linguagem = ?, sinopse = ? WHERE Id_Filme = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getTitulo());
            stmt.setBigDecimal(2, filme.getDuracao());
            stmt.setString(3, filme.getLinguagem());
            stmt.setString(4, filme.getSinopse());
            stmt.setInt(5, filme.getIdFilme());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int idFilme) {
        String sql = "DELETE FROM Filme WHERE Id_Filme = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFilme);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Filme mapRow(ResultSet rs) throws SQLException {
        Filme filme = new Filme();
        filme.setIdFilme(rs.getInt("Id_Filme"));
        filme.setTitulo(rs.getString("titulo"));
        filme.setDuracao(rs.getBigDecimal("duracao"));
        filme.setLinguagem(rs.getString("linguagem"));
        filme.setSinopse(rs.getString("sinopse"));
        return filme;
    }
}
