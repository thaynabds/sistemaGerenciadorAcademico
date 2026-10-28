package br.com.fuctura.academico.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.fuctura.academico.entity.Disciplina;
import br.com.fuctura.academico.infrastructure.ConnectionFactory;

public class DisciplinaDAO {

    private static final String CONSULTA = """
            SELECT d.codigo, d.nome, d.carga_horaria, d.professor_matricula, p.nome AS professor
            FROM disciplina d
            LEFT JOIN professor p ON p.matricula = d.professor_matricula
            """;

    private final ConnectionFactory conexoes;

    public DisciplinaDAO(ConnectionFactory conexoes) {
        this.conexoes = conexoes;
    }

    public void inserir(Disciplina disciplina) {
        String sql = """
                INSERT INTO disciplina (codigo, nome, carga_horaria, professor_matricula)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, disciplina.getCodigo());
            comando.setString(2, disciplina.getNome());
            comando.setString(3, disciplina.getCargaHoraria());
            comando.setString(4, disciplina.getProfessorMatricula());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean atualizar(Disciplina disciplina) {
        String sql = """
                UPDATE disciplina
                SET nome = ?, carga_horaria = ?, professor_matricula = ?
                WHERE codigo = ?
                """;
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, disciplina.getNome());
            comando.setString(2, disciplina.getCargaHoraria());
            comando.setString(3, disciplina.getProfessorMatricula());
            comando.setString(4, disciplina.getCodigo());
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Disciplina> listar() {
        String sql = CONSULTA + " ORDER BY d.nome";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {
            List<Disciplina> disciplinas = new ArrayList<>();
            while (resultado.next()) {
                disciplinas.add(mapear(resultado));
            }
            return disciplinas;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public Optional<Disciplina> buscarPorCodigo(String codigo) {
        String sql = CONSULTA + " WHERE d.codigo = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, codigo);
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

    public boolean existePorProfessor(String professorMatricula) {
        String sql = "SELECT 1 FROM disciplina WHERE professor_matricula = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, professorMatricula);
            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean excluir(String codigo) {
        String sql = "DELETE FROM disciplina WHERE codigo = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, codigo);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private Disciplina mapear(ResultSet resultado) throws SQLException {
        Disciplina disciplina = new Disciplina(
                resultado.getString("codigo"),
                resultado.getString("nome"),
                resultado.getString("carga_horaria"),
                resultado.getString("professor_matricula"));
        disciplina.setProfessorNome(resultado.getString("professor"));
        return disciplina;
    }
}
