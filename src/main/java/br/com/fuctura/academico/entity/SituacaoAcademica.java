package br.com.fuctura.academico.entity;

import java.time.LocalDate;

public record SituacaoAcademica(String aluno, String disciplina, String professor, LocalDate dataMatricula) {
}
