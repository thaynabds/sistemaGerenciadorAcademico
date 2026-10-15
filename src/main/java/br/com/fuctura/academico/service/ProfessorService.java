package br.com.fuctura.academico.service;

import java.util.List;

import br.com.fuctura.academico.dao.ProfessorDAO;
import br.com.fuctura.academico.entity.Professor;

public class ProfessorService {

    private final ProfessorDAO professorDao;

    public ProfessorService(ProfessorDAO professorDao) {
        this.professorDao = professorDao;
    }

    public void cadastrar(Professor professor) {
        professor.setMatricula(texto(professor.getMatricula(), "Informe a matrícula."));
        professor.setNome(texto(professor.getNome(), "Informe o nome."));
        professor.setTelefone(texto(professor.getTelefone(), "Informe o telefone."));
        if (professorDao.buscarPorMatricula(professor.getMatricula()).isPresent()) {
            throw new IllegalArgumentException("Já existe um professor com essa matrícula.");
        }
        professorDao.inserir(professor);
    }

    public void alterar(Professor professor) {
        professor.setMatricula(texto(professor.getMatricula(), "Informe a matrícula."));
        professor.setNome(texto(professor.getNome(), "Informe o nome."));
        professor.setTelefone(texto(professor.getTelefone(), "Informe o telefone."));
        if (!professorDao.atualizar(professor)) {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
    }

    public List<Professor> listar() {
        return professorDao.listar();
    }

    public Professor buscar(String matricula) {
        return professorDao.buscarPorMatricula(texto(matricula, "Informe a matrícula."))
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado."));
    }

    public void excluir(String matricula) {
        if (!professorDao.excluir(texto(matricula, "Informe a matrícula."))) {
            throw new IllegalArgumentException("Professor não encontrado.");
        }
    }

    private String texto(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor.trim();
    }
}
