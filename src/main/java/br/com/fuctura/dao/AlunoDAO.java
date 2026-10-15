package br.com.fuctura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.fuctura.domain.Aluno;
import br.com.fuctura.infrastructure.ConnectionFactory;

public class AlunoDAO {

    private final ConnectionFactory conexoes;

    public AlunoDAO(ConnectionFactory conexoes) {
        this.conexoes = conexoes;
    }

    public void inserir(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, idade, celular) VALUES (?, ?, ?, ?)";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, aluno.getMatricula());
            comando.setString(2, aluno.getNome());
            comando.setInt(3, aluno.getIdade());
            comando.setString(4, aluno.getCelular());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Aluno> listar() {
        String sql = "SELECT matricula, nome, idade, celular FROM aluno ORDER BY nome";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {
            List<Aluno> alunos = new ArrayList<>();
            while (resultado.next()) {
                alunos.add(mapear(resultado));
            }
            return alunos;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Optional<Aluno> buscarPorMatricula(String matricula) {
        String sql = "SELECT matricula, nome, idade, celular FROM aluno WHERE matricula = ?";
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
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, matricula);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Aluno mapear(ResultSet resultado) throws SQLException {
        return new Aluno(
                resultado.getString("matricula"),
                resultado.getString("nome"),
                resultado.getInt("idade"),
                resultado.getString("celular"));
    }
}
