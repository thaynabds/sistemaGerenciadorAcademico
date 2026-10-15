package br.com.fuctura.domain;

import java.util.Objects;

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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
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
