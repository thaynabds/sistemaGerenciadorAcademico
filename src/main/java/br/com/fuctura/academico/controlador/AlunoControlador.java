package br.com.fuctura.academico.controlador;

import java.util.List;

import br.com.fuctura.academico.entity.Aluno;
import br.com.fuctura.academico.service.AlunoService;

public class AlunoControlador {

    private final AlunoService alunoService;

    public AlunoControlador(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    public void cadastrar(Aluno aluno) {
        alunoService.cadastrar(aluno);
    }

    public void alterar(Aluno aluno) {
        alunoService.alterar(aluno);
    }

    public List<Aluno> listar() {
        return alunoService.listar();
    }

    public Aluno buscar(String matricula) {
        return alunoService.buscar(matricula);
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoService.buscarPorNome(nome);
    }

    public void excluir(String matricula) {
        alunoService.excluir(matricula);
    }
}
