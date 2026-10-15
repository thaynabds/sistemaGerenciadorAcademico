package br.com.fuctura.domain;

public class Disciplina {

    private String codigo;
    private String nome;
    private String cargaHoraria;
    private String professorMatricula;
    private String professorNome;

    public Disciplina() {
    }

    public Disciplina(String codigo, String nome, String cargaHoraria, String professorMatricula) {
        this.codigo = codigo;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professorMatricula = professorMatricula;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getProfessorMatricula() {
        return professorMatricula;
    }

    public void setProfessorMatricula(String professorMatricula) {
        this.professorMatricula = professorMatricula;
    }

    public String getProfessorNome() {
        return professorNome;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }
}
