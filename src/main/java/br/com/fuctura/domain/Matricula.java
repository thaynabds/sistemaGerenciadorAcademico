package br.com.fuctura.domain;

import java.time.LocalDate;

public class Matricula {

    private Long id;
    private LocalDate dataMatricula;
    private String alunoMatricula;
    private String disciplinaCodigo;

    public Matricula() {
    }

    public Matricula(Long id, LocalDate dataMatricula, String alunoMatricula, String disciplinaCodigo) {
        this.id = id;
        this.dataMatricula = dataMatricula;
        this.alunoMatricula = alunoMatricula;
        this.disciplinaCodigo = disciplinaCodigo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getAlunoMatricula() {
        return alunoMatricula;
    }

    public void setAlunoMatricula(String alunoMatricula) {
        this.alunoMatricula = alunoMatricula;
    }

    public String getDisciplinaCodigo() {
        return disciplinaCodigo;
    }

    public void setDisciplinaCodigo(String disciplinaCodigo) {
        this.disciplinaCodigo = disciplinaCodigo;
    }
}
