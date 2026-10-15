package br.com.fuctura.academico.service;

import java.util.List;

import br.com.fuctura.academico.dao.AlunoDAO;
import br.com.fuctura.academico.entity.Aluno;

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

    public void alterar(Aluno aluno) {
        aluno.setMatricula(texto(aluno.getMatricula(), "Informe a matrícula."));
        aluno.setNome(texto(aluno.getNome(), "Informe o nome."));
        aluno.setCelular(texto(aluno.getCelular(), "Informe o celular."));
        if (aluno.getIdade() == null || aluno.getIdade() < 1) {
            throw new IllegalArgumentException("Informe uma idade válida.");
        }
        if (!alunoDao.atualizar(aluno)) {
            throw new IllegalArgumentException("Aluno não encontrado.");
        }
    }

    public List<Aluno> listar() {
        return alunoDao.listar();
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoDao.buscarPorNome(texto(nome, "Informe o nome."));
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
