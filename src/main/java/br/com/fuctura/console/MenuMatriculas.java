package br.com.fuctura.console;

import br.com.fuctura.domain.Aluno;
import br.com.fuctura.domain.Disciplina;
import br.com.fuctura.service.AlunoService;
import br.com.fuctura.service.DisciplinaService;
import br.com.fuctura.service.MatriculaService;

public final class MenuMatriculas {

    private final ConsoleUI ui;
    private final MatriculaService matriculas;
    private final DisciplinaService disciplinas;
    private final AlunoService alunos;

    public MenuMatriculas(ConsoleUI ui, MatriculaService matriculas, DisciplinaService disciplinas, AlunoService alunos) {
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
            ui.imprimir("3 - Consultar disciplinas de um aluno");
            ui.imprimir("4 - Consultar alunos de uma disciplina");
            ui.imprimir("5 - Relatório completo");
            ui.imprimir("0 - Voltar");
            ui.imprimir("");
            opcao = ui.ler("Escolha: ");
            switch (opcao) {
                case "1" -> ui.executar(this::matricular);
                case "2" -> ui.executar(this::cancelar);
                case "3" -> ui.executar(this::disciplinasDoAluno);
                case "4" -> ui.executar(this::alunosDaDisciplina);
                case "5" -> ui.executar(this::relatorio);
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
        ui.imprimir(String.format("%-20s %-22s %s", "Aluno", "Disciplina", "Professor"));
        ui.imprimir("---------------------------------------------------------");
        linhas.forEach(linha -> ui.imprimir(String.format("%-20s %-22s %s", linha[0], linha[1], linha[2])));
    }
}
