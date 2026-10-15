package br.com.fuctura.academico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.fuctura.academico.entity.Professor;
import br.com.fuctura.academico.infrastructure.ConnectionFactory;

public class ProfessorDAO {

    private final ConnectionFactory conexoes;

    public ProfessorDAO(ConnectionFactory conexoes) {
        this.conexoes = conexoes;
    }

    public void inserir(Professor professor) {
        String sql = "INSERT INTO professor (nome, telefone, matricula) VALUES (?, ?, ?)";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, professor.getNome());
            comando.setString(2, professor.getTelefone());
            comando.setString(3, professor.getMatricula());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean atualizar(Professor professor) {
        String sql = "UPDATE professor SET nome = ?, telefone = ? WHERE matricula = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, professor.getNome());
            comando.setString(2, professor.getTelefone());
            comando.setString(3, professor.getMatricula());
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Professor> listar() {
        String sql = "SELECT matricula, nome, telefone FROM professor ORDER BY nome";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {
            List<Professor> professores = new ArrayList<>();
            while (resultado.next()) {
                professores.add(mapear(resultado));
            }
            return professores;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Optional<Professor> buscarPorMatricula(String matricula) {
        String sql = "SELECT matricula, nome, telefone FROM professor WHERE matricula = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, matricula);
            try (ResultSet resultado = comando.executeQuery()) {
                if (!resultado.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapear(resultado));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean excluir(String matricula) {
        String sql = "DELETE FROM professor WHERE matricula = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, matricula);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Professor mapear(ResultSet resultado) throws SQLException {
        return new Professor(
                resultado.getString("matricula"),
                resultado.getString("nome"),
                resultado.getString("telefone"));
    }
}
