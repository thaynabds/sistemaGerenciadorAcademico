package br.com.fuctura.service;

import java.util.List;

import br.com.fuctura.dao.AlunoDAO;
import br.com.fuctura.domain.Aluno;

public class AlunoService {

    private final AlunoDAO alunoDao;

    public AlunoService(AlunoDAO alunoDao) {
        this.alunoDao = alunoDao;
    }

    public void cadastrar(Aluno aluno) {
        aluno.setMatricula(texto(aluno.getMatricula(), "Informe a matrícula."));
        aluno.setNome(texto(aluno.getNome(), "Informe o nome."));
        aluno.setCelular(texto(aluno.getCelular(), "Informe o celular."));
        if (aluno.getIdade() == null || aluno.getIdade() < 1) {
            throw new IllegalArgumentException("Informe uma idade válida.");
        }
        if (alunoDao.buscarPorMatricula(aluno.getMatricula()).isPresent()) {
            throw new IllegalArgumentException("Já existe um aluno com essa matrícula.");
        }
        alunoDao.inserir(aluno);
    }

    public List<Aluno> listar() {
        return alunoDao.listar();
    }

    public Aluno buscar(String matricula) {
        return alunoDao.buscarPorMatricula(texto(matricula, "Informe a matrícula."))
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));
    }

    public void excluir(String matricula) {
        if (!alunoDao.excluir(texto(matricula, "Informe a matrícula."))) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
    }

    private String texto(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagem);
        }
        return valor.trim();
    }
}
