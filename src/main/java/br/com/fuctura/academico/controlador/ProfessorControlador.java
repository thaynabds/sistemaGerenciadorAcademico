package br.com.fuctura.academico.controlador;

import java.util.List;

import br.com.fuctura.academico.entity.Professor;
import br.com.fuctura.academico.service.ProfessorService;

public class ProfessorControlador {

    private final ProfessorService professorService;

    public ProfessorControlador(ProfessorService professorService) {
        this.professorService = professorService;
    }

    public void cadastrar(Professor professor) {
        professorService.cadastrar(professor);
    }

    public void alterar(Professor professor) {
        professorService.alterar(professor);
    }

    public List<Professor> listar() {
        return professorService.listar();
    }

    public Professor buscar(String matricula) {
        return professorService.buscar(matricula);
    }

    public void excluir(String matricula) {
        professorService.excluir(matricula);
    }
}
