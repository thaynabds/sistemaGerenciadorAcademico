package br.com.fuctura.academico.entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
