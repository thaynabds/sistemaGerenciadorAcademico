package br.com.fuctura.academico.entity;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Professor {

    private String matricula;
    private String nome;
    private String telefone;

    public Professor() {
    }

    public Professor(String matricula, String nome, String telefone) {
        this.matricula = matricula;
        this.nome = nome;
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof Professor professor)) {
            return false;
        }
        return Objects.equals(matricula, professor.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }
}
