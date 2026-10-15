package br.com.fuctura.academico.controlador;

import java.util.List;

import br.com.fuctura.academico.entity.Disciplina;
import br.com.fuctura.academico.service.DisciplinaService;

public class DisciplinaControlador {

    private final DisciplinaService disciplinaService;

    public DisciplinaControlador(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    public void cadastrar(Disciplina disciplina) {
        disciplinaService.cadastrar(disciplina);
    }

    public void alterar(Disciplina disciplina) {
        disciplinaService.alterar(disciplina);
    }

    public List<Disciplina> listar() {
        return disciplinaService.listar();
    }

    public Disciplina buscar(String codigo) {
        return disciplinaService.buscar(codigo);
    }

    public void excluir(String codigo) {
        disciplinaService.excluir(codigo);
    }
}
