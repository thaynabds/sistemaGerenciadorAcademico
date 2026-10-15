package br.com.fuctura.academico.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
