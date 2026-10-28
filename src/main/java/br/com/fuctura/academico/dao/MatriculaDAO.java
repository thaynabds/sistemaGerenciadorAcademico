package br.com.fuctura.academico.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fuctura.academico.entity.Aluno;
import br.com.fuctura.academico.entity.Disciplina;
import br.com.fuctura.academico.entity.Matricula;
import br.com.fuctura.academico.entity.SituacaoAcademica;
import br.com.fuctura.academico.infrastructure.ConnectionFactory;

public class MatriculaDAO {

    private final ConnectionFactory conexoes;

    public MatriculaDAO(ConnectionFactory conexoes) {
        this.conexoes = conexoes;
    }

    public void inserir(Matricula matricula) {
        String sql = """
                INSERT INTO matricula (data_matricula, aluno_matricula, disciplina_codigo)
                VALUES (?, ?, ?)
                """;
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setDate(1, Date.valueOf(matricula.getDataMatricula()));
            comando.setString(2, matricula.getAlunoMatricula());
            comando.setString(3, matricula.getDisciplinaCodigo());
            comando.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean excluir(String alunoMatricula, String disciplinaCodigo) {
        String sql = "DELETE FROM matricula WHERE aluno_matricula = ? AND disciplina_codigo = ?";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, alunoMatricula);
            comando.setString(2, disciplinaCodigo);
            return comando.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean existe(String alunoMatricula, String disciplinaCodigo) {
        String sql = """
                SELECT 1 FROM matricula
                WHERE aluno_matricula = ? AND disciplina_codigo = ?
                """;
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, alunoMatricula);
            comando.setString(2, disciplinaCodigo);
            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public boolean existePorAluno(String alunoMatricula) {
        return existeAlgum("SELECT 1 FROM matricula WHERE aluno_matricula = ?", alunoMatricula);
    }

    public boolean existePorDisciplina(String disciplinaCodigo) {
        return existeAlgum("SELECT 1 FROM matricula WHERE disciplina_codigo = ?", disciplinaCodigo);
    }

    private boolean existeAlgum(String sql, String valor) {
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, valor);
            try (ResultSet resultado = comando.executeQuery()) {
                return resultado.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Disciplina> listarDisciplinasDoAluno(String alunoMatricula) {
        String sql = """
                SELECT d.codigo, d.nome, d.carga_horaria, d.professor_matricula
                FROM matricula m
                INNER JOIN disciplina d ON d.codigo = m.disciplina_codigo
                WHERE m.aluno_matricula = ?
                ORDER BY d.nome
                """;
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, alunoMatricula);
            try (ResultSet resultado = comando.executeQuery()) {
                List<Disciplina> disciplinas = new ArrayList<>();
                while (resultado.next()) {
                    disciplinas.add(new Disciplina(
                            resultado.getString("codigo"),
                            resultado.getString("nome"),
                            resultado.getString("carga_horaria"),
                            resultado.getString("professor_matricula")));
                }
                return disciplinas;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<Aluno> listarAlunosDaDisciplina(String disciplinaCodigo) {
        String sql = """
                SELECT a.matricula, a.nome, a.idade, a.celular
                FROM matricula m
                INNER JOIN aluno a ON a.matricula = m.aluno_matricula
                WHERE m.disciplina_codigo = ?
                ORDER BY a.nome
                """;
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, disciplinaCodigo);
            try (ResultSet resultado = comando.executeQuery()) {
                List<Aluno> alunos = new ArrayList<>();
                while (resultado.next()) {
                    alunos.add(new Aluno(
                            resultado.getString("matricula"),
                            resultado.getString("nome"),
                            resultado.getInt("idade"),
                            resultado.getString("celular")));
                }
                return alunos;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static final String SITUACAO = """
            SELECT a.nome AS aluno, d.nome AS disciplina, p.nome AS professor, m.data_matricula
            FROM matricula m
            INNER JOIN aluno a ON a.matricula = m.aluno_matricula
            INNER JOIN disciplina d ON d.codigo = m.disciplina_codigo
            INNER JOIN professor p ON p.matricula = d.professor_matricula
            """;

    public List<SituacaoAcademica> listarRelatorio() {
        String sql = SITUACAO + " ORDER BY a.nome, d.nome";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql);
                ResultSet resultado = comando.executeQuery()) {
            return mapearSituacao(resultado);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public List<SituacaoAcademica> listarSituacaoDoAluno(String alunoMatricula) {
        String sql = SITUACAO + " WHERE m.aluno_matricula = ? ORDER BY d.nome";
        try (Connection conexao = conexoes.abrir();
                PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, alunoMatricula);
            try (ResultSet resultado = comando.executeQuery()) {
                return mapearSituacao(resultado);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<SituacaoAcademica> mapearSituacao(ResultSet resultado) throws SQLException {
        List<SituacaoAcademica> linhas = new ArrayList<>();
        while (resultado.next()) {
            linhas.add(new SituacaoAcademica(
                    resultado.getString("aluno"),
                    resultado.getString("disciplina"),
                    resultado.getString("professor"),
                    resultado.getDate("data_matricula").toLocalDate()));
        }
        return linhas;
    }
}
