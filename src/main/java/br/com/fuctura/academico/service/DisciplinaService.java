package br.com.fuctura.academico.service;

import java.util.List;

import br.com.fuctura.academico.dao.DisciplinaDAO;
import br.com.fuctura.academico.dao.ProfessorDAO;
import br.com.fuctura.academico.entity.Disciplina;

public class DisciplinaService {

    private final DisciplinaDAO disciplinaDao;
    private final ProfessorDAO professorDao;

    public DisciplinaService(DisciplinaDAO disciplinaDao, ProfessorDAO professorDao) {
        this.disciplinaDao = disciplinaDao;
        this.professorDao = professorDao;
    }

    public void cadastrar(Disciplina disciplina) {
        disciplina.setCodigo(texto(disciplina.getCodigo(), "Informe o código."));
        disciplina.setNome(texto(disciplina.getNome(), "Informe o nome."));
        disciplina.setCargaHoraria(texto(disciplina.getCargaHoraria(), "Informe a carga horária."));
        disciplina.setProfessorMatricula(texto(disciplina.getProfessorMatricula(), "Informe a matrícula do professor."));
        if (professorDao.buscarPorMatricula(disciplina.getProfessorMatricula()).isEmpty()) {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
        if (disciplinaDao.buscarPorCodigo(disciplina.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma disciplina com esse código.");
        }
        disciplinaDao.inserir(disciplina);
    }

    public void alterar(Disciplina disciplina) {
        disciplina.setCodigo(texto(disciplina.getCodigo(), "Informe o código."));
        disciplina.setNome(texto(disciplina.getNome(), "Informe o nome."));
        disciplina.setCargaHoraria(texto(disciplina.getCargaHoraria(), "Informe a carga horária."));
        disciplina.setProfessorMatricula(texto(disciplina.getProfessorMatricula(), "Informe a matrícula do professor."));
        if (professorDao.buscarPorMatricula(disciplina.getProfessorMatricula()).isEmpty()) {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
        if (!disciplinaDao.atualizar(disciplina)) {
            throw new IllegalArgumentException("Disciplina não encontrada.");
        }
    }

    public List<Disciplina> listar() {
        return disciplinaDao.listar();
    }

    public Disciplina buscar(String codigo) {
        return disciplinaDao.buscarPorCodigo(texto(codigo, "Informe o código."))
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada."));
    }

    public void excluir(String codigo) {
        if (!disciplinaDao.excluir(texto(codigo, "Informe o código."))) {
            throw new IllegalArgumentException("Disciplina não encontrada.");
        }
    }

    private String texto(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor.trim();
    }
}
