package br.com.fuctura.academico.console;

import java.time.format.DateTimeFormatter;

import br.com.fuctura.academico.controlador.AlunoControlador;
import br.com.fuctura.academico.controlador.DisciplinaControlador;
import br.com.fuctura.academico.controlador.MatriculaControlador;
import br.com.fuctura.academico.entity.Aluno;
import br.com.fuctura.academico.entity.Disciplina;

public final class MenuMatriculas {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ConsoleUI ui;
    private final MatriculaControlador matriculas;
    private final DisciplinaControlador disciplinas;
    private final AlunoControlador alunos;

    public MenuMatriculas(ConsoleUI ui, MatriculaControlador matriculas, DisciplinaControlador disciplinas, AlunoControlador alunos) {
        this.ui = ui;
        this.matriculas = matriculas;
        this.disciplinas = disciplinas;
        this.alunos = alunos;
    }

    public void abrir() {
        String opcao;
        do {
            ui.imprimir("");
            ui.imprimir("=================================");
            ui.imprimir("         MATRÍCULAS");
            ui.imprimir("=================================");
            ui.imprimir("");
            ui.imprimir("1 - Matricular aluno");
            ui.imprimir("2 - Cancelar matrícula");
            ui.imprimir("3 - Disciplinas do aluno");
            ui.imprimir("4 - Alunos da disciplina");
            ui.imprimir("5 - Consultar situação acadêmica");
            ui.imprimir("6 - Relatório completo");
            ui.imprimir("0 - Voltar");
            ui.imprimir("");
            opcao = ui.ler("Escolha uma opção: ");
            switch (opcao) {
                case "1" -> ui.executar(this::matricular);
                case "2" -> ui.executar(this::cancelar);
                case "3" -> ui.executar(this::disciplinasDoAluno);
                case "4" -> ui.executar(this::alunosDaDisciplina);
                case "5" -> ui.executar(this::situacaoAcademica);
                case "6" -> ui.executar(this::relatorio);
                case "0" -> {
                }
                default -> ui.imprimir("Opção inválida.");
            }
        } while (!"0".equals(opcao));
    }

    private void matricular() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      MATRICULAR ALUNO");
        ui.imprimir("=================================");
        ui.imprimir("");
        var oferta = disciplinas.listar();
        if (oferta.isEmpty()) {
            ui.imprimir("Nenhuma disciplina disponível.");
            return;
        }
        String aluno = ui.ler("Matrícula do aluno: ");
        ui.imprimir("");
        ui.imprimir("Disciplinas disponíveis:");
        ui.imprimir("");
        oferta.forEach(disciplina -> ui.imprimir(disciplina.getCodigo() + " - " + disciplina.getNome()));
        ui.imprimir("");
        String codigo = ui.ler("Código da disciplina: ");
        matriculas.matricular(aluno, codigo);
        ui.imprimir("");
        ui.imprimir("Matrícula realizada com sucesso!");
    }

    private void cancelar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      CANCELAR MATRÍCULA");
        ui.imprimir("=================================");
        ui.imprimir("");
        String aluno = ui.ler("Matrícula do aluno: ");
        alunos.buscar(aluno);
        var lista = matriculas.listarDisciplinasDoAluno(aluno);
        ui.imprimir("");
        ui.imprimir("Disciplinas matriculadas:");
        ui.imprimir("");
        if (lista.isEmpty()) {
            ui.imprimir("O aluno não possui matrículas.");
            return;
        }
        lista.forEach(disciplina -> ui.imprimir(disciplina.getCodigo() + " - " + disciplina.getNome()));
        ui.imprimir("");
        String codigo = ui.ler("Código da disciplina: ");
        matriculas.cancelar(aluno, codigo);
        ui.imprimir("");
        ui.imprimir("Matrícula cancelada com sucesso!");
    }

    private void disciplinasDoAluno() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("    DISCIPLINAS DO ALUNO");
        ui.imprimir("=================================");
        ui.imprimir("");
        Aluno aluno = alunos.buscar(ui.ler("Matrícula do aluno: "));
        var lista = matriculas.listarDisciplinasDoAluno(aluno.getMatricula());
        ui.imprimir("");
        ui.imprimir("Aluno: " + aluno.getNome());
        ui.imprimir("");
        ui.imprimir("Disciplinas:");
        ui.imprimir("");
        if (lista.isEmpty()) {
            ui.imprimir("Nenhuma disciplina encontrada.");
            return;
        }
        lista.forEach(disciplina -> ui.imprimir(disciplina.getCodigo() + " - " + disciplina.getNome()));
    }

    private void alunosDaDisciplina() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("   ALUNOS DA DISCIPLINA");
        ui.imprimir("=================================");
        ui.imprimir("");
        Disciplina disciplina = disciplinas.buscar(ui.ler("Código da disciplina: "));
        var lista = matriculas.listarAlunosDaDisciplina(disciplina.getCodigo());
        String professor = disciplina.getProfessorNome() == null ? "" : disciplina.getProfessorNome();
        ui.imprimir("");
        ui.imprimir("Disciplina: " + disciplina.getNome());
        ui.imprimir("Professor: " + professor);
        ui.imprimir("");
        ui.imprimir("Alunos matriculados:");
        ui.imprimir("");
        if (lista.isEmpty()) {
            ui.imprimir("Nenhum aluno matriculado.");
            return;
        }
        lista.forEach(aluno -> ui.imprimir(aluno.getMatricula() + " - " + aluno.getNome()));
    }

    private void situacaoAcademica() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("    SITUAÇÃO ACADÊMICA");
        ui.imprimir("=================================");
        ui.imprimir("");
        Aluno aluno = alunos.buscar(ui.ler("Matrícula do aluno: "));
        var linhas = matriculas.situacaoDoAluno(aluno.getMatricula());
        ui.imprimir("");
        ui.imprimir("Aluno: " + aluno.getNome());
        ui.imprimir("Idade: " + aluno.getIdade());
        ui.imprimir("Celular: " + aluno.getCelular());
        ui.imprimir("Total de disciplinas: " + linhas.size());
        ui.imprimir("");
        if (linhas.isEmpty()) {
            ui.imprimir("O aluno não possui matrículas.");
            return;
        }
        ui.imprimir(String.format("%-22s %-20s %s", "Disciplina", "Professor", "Data"));
        ui.imprimir("----------------------------------------------------------");
        linhas.forEach(linha -> ui.imprimir(String.format(
                "%-22s %-20s %s",
                linha.disciplina(),
                linha.professor(),
                linha.dataMatricula().format(DATA))));
    }

    private void relatorio() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      RELATÓRIO COMPLETO");
        ui.imprimir("=================================");
        ui.imprimir("");
        var linhas = matriculas.relatorio();
        if (linhas.isEmpty()) {
            ui.imprimir("Nenhuma matrícula registrada.");
            return;
        }
        ui.imprimir(String.format("%-20s %-22s %-20s %s", "Aluno", "Disciplina", "Professor", "Data"));
        ui.imprimir("----------------------------------------------------------------------");
        linhas.forEach(linha -> ui.imprimir(String.format(
                "%-20s %-22s %-20s %s",
                linha.aluno(),
                linha.disciplina(),
                linha.professor(),
                linha.dataMatricula().format(DATA))));
    }
}
