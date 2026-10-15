package br.com.fuctura.academico.controlador;

import java.util.List;

import br.com.fuctura.academico.entity.Aluno;
import br.com.fuctura.academico.entity.Disciplina;
import br.com.fuctura.academico.entity.SituacaoAcademica;
import br.com.fuctura.academico.service.MatriculaService;

public class MatriculaControlador {

    private final MatriculaService matriculaService;

    public MatriculaControlador(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    public void matricular(String alunoMatricula, String disciplinaCodigo) {
        matriculaService.matricular(alunoMatricula, disciplinaCodigo);
    }

    public void cancelar(String alunoMatricula, String disciplinaCodigo) {
        matriculaService.cancelar(alunoMatricula, disciplinaCodigo);
    }

    public List<Disciplina> listarDisciplinasDoAluno(String alunoMatricula) {
        return matriculaService.listarDisciplinasDoAluno(alunoMatricula);
    }

    public List<Aluno> listarAlunosDaDisciplina(String disciplinaCodigo) {
        return matriculaService.listarAlunosDaDisciplina(disciplinaCodigo);
    }

    public List<SituacaoAcademica> relatorio() {
        return matriculaService.relatorio();
    }
}
