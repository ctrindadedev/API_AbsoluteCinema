package com.ufrn.imd.cinema.repository.sessao;

import com.ufrn.imd.cinema.models.sessao.Sessao;
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
public class SessaoDAO {

    private final DataSource dataSource;

    public SessaoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Sessao sessao) {
        String sql = "INSERT INTO Sessao (Id_Sessao, data, hora_inicial, hora_final, tipo, valor_sessao, Sala_Id_Sala, Administrativo_Cpf, Filme_Id_Filme) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getIdSessao());
            stmt.setDate(2, Date.valueOf(sessao.getData()));
            stmt.setTime(3, Time.valueOf(sessao.getHoraInicial()));
            stmt.setTime(4, Time.valueOf(sessao.getHoraFinal()));
            stmt.setString(5, sessao.getTipo());
            stmt.setFloat(6, sessao.getValorSessao());
            stmt.setInt(7, sessao.getSalaIdSala());
            stmt.setLong(8, sessao.getAdministrativoCpf());
            stmt.setInt(9, sessao.getFilmeIdFilme());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public Optional<Sessao> buscarPorId(int idSessao) {
        String sql = "SELECT Id_Sessao, data, hora_inicial, hora_final, tipo, valor_sessao, Sala_Id_Sala, Administrativo_Cpf, Filme_Id_Filme "
                + "FROM Sessao WHERE Id_Sessao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSessao);

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

    public List<Sessao> buscarTodas() {
        String sql = "SELECT Id_Sessao, data, hora_inicial, hora_final, tipo, valor_sessao, Sala_Id_Sala, Administrativo_Cpf, Filme_Id_Filme FROM Sessao";
        List<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                sessoes.add(mapRow(rs));
            }
            return sessoes;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean atualizar(Sessao sessao) {
        String sql = "UPDATE Sessao SET data = ?, hora_inicial = ?, hora_final = ?, tipo = ?, valor_sessao = ?, "
                + "Sala_Id_Sala = ?, Administrativo_Cpf = ?, Filme_Id_Filme = ? WHERE Id_Sessao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(sessao.getData()));
            stmt.setTime(2, Time.valueOf(sessao.getHoraInicial()));
            stmt.setTime(3, Time.valueOf(sessao.getHoraFinal()));
            stmt.setString(4, sessao.getTipo());
            stmt.setFloat(5, sessao.getValorSessao());
            stmt.setInt(6, sessao.getSalaIdSala());
            stmt.setLong(7, sessao.getAdministrativoCpf());
            stmt.setInt(8, sessao.getFilmeIdFilme());
            stmt.setInt(9, sessao.getIdSessao());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    public boolean deletar(int idSessao) {
        String sql = "DELETE FROM Sessao WHERE Id_Sessao = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSessao);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar no banco via JDBC: " + e.getMessage(), e);
        }
    }

    private Sessao mapRow(ResultSet rs) throws SQLException {
        Sessao sessao = new Sessao();
        sessao.setIdSessao(rs.getInt("Id_Sessao"));
        sessao.setData(rs.getDate("data").toLocalDate());
        sessao.setHoraInicial(rs.getTime("hora_inicial").toLocalTime());
        sessao.setHoraFinal(rs.getTime("hora_final").toLocalTime());
        sessao.setTipo(rs.getString("tipo"));
        sessao.setValorSessao(rs.getFloat("valor_sessao"));
        sessao.setSalaIdSala(rs.getInt("Sala_Id_Sala"));
        sessao.setAdministrativoCpf(rs.getLong("Administrativo_Cpf"));
        sessao.setFilmeIdFilme(rs.getInt("Filme_Id_Filme"));
        return sessao;
    }
}
