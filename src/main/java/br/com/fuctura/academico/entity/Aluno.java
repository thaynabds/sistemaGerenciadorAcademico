package br.com.fuctura.academico.entity;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Aluno {

    private String matricula;
    private String nome;
    private Integer idade;
    private String celular;

    public Aluno() {
    }

    public Aluno(String matricula, String nome, Integer idade, String celular) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.celular = celular;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Aluno aluno)) {
            return false;
        }
        return Objects.equals(matricula, aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}
