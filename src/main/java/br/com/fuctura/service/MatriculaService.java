package br.com.fuctura.service;

import java.time.LocalDate;
import java.util.List;

import br.com.fuctura.dao.AlunoDAO;
import br.com.fuctura.dao.DisciplinaDAO;
import br.com.fuctura.dao.MatriculaDAO;
import br.com.fuctura.domain.Aluno;
import br.com.fuctura.domain.Disciplina;
import br.com.fuctura.domain.Matricula;

public class MatriculaService {

    private final AlunoDAO alunoDao;
    private final DisciplinaDAO disciplinaDao;
    private final MatriculaDAO matriculaDao;

    public MatriculaService(AlunoDAO alunoDao, DisciplinaDAO disciplinaDao, MatriculaDAO matriculaDao) {
        this.alunoDao = alunoDao;
        this.disciplinaDao = disciplinaDao;
        this.matriculaDao = matriculaDao;
    }

    public void matricular(String alunoMatricula, String disciplinaCodigo) {
        String matriculaAluno = alunoMatricula == null ? "" : alunoMatricula.trim();
        String codigo = disciplinaCodigo == null ? "" : disciplinaCodigo.trim();
        if (alunoDao.buscarPorMatricula(matriculaAluno).isEmpty()) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
        if (disciplinaDao.buscarPorCodigo(codigo).isEmpty()) {
            throw new IllegalArgumentException("Disciplina não encontrada.");
        }
        if (matriculaDao.existe(matriculaAluno, codigo)) {
            throw new IllegalArgumentException("O aluno já está matriculado nesta disciplina.");
        }
        matriculaDao.inserir(new Matricula(null, LocalDate.now(), matriculaAluno, codigo));
    }

    public void cancelar(String alunoMatricula, String disciplinaCodigo) {
        String matriculaAluno = alunoMatricula == null ? "" : alunoMatricula.trim();
        String codigo = disciplinaCodigo == null ? "" : disciplinaCodigo.trim();
        if (!matriculaDao.excluir(matriculaAluno, codigo)) {
            throw new IllegalArgumentException("Matrícula não encontrada.");
        }
    }

    public List<Disciplina> listarDisciplinasDoAluno(String alunoMatricula) {
        return matriculaDao.listarDisciplinasDoAluno(alunoMatricula.trim());
    }

    public List<Aluno> listarAlunosDaDisciplina(String disciplinaCodigo) {
        return matriculaDao.listarAlunosDaDisciplina(disciplinaCodigo.trim());
    }

    public List<String[]> relatorio() {
        return matriculaDao.listarRelatorio();
    }
}
