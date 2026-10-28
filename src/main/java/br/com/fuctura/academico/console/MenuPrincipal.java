package br.com.fuctura.academico.console;

import br.com.fuctura.academico.controlador.AlunoControlador;
import br.com.fuctura.academico.controlador.DisciplinaControlador;
import br.com.fuctura.academico.controlador.MatriculaControlador;
import br.com.fuctura.academico.controlador.ProfessorControlador;

public final class MenuPrincipal {

    private final ConsoleUI ui;
    private final MenuAlunos alunos;
    private final MenuProfessores professores;
    private final MenuDisciplinas disciplinas;
    private final MenuMatriculas matriculas;

    public MenuPrincipal(
            ConsoleUI ui,
            AlunoControlador alunos,
            ProfessorControlador professores,
            DisciplinaControlador disciplinas,
            MatriculaControlador matriculas) {
        this.ui = ui;
        this.alunos = new MenuAlunos(ui, alunos);
        this.professores = new MenuProfessores(ui, professores);
        this.disciplinas = new MenuDisciplinas(ui, disciplinas, professores);
        this.matriculas = new MenuMatriculas(ui, matriculas, disciplinas, alunos);
    }

    public void abrir() {
        String opcao;
        do {
            ui.imprimir("");
            ui.imprimir("=================================");
            ui.imprimir("     SISTEMA ACADÊMICO");
            ui.imprimir("=================================");
            ui.imprimir("");
            ui.imprimir("1 - Alunos");
            ui.imprimir("2 - Professores");
            ui.imprimir("3 - Disciplinas");
            ui.imprimir("4 - Matrículas");
            ui.imprimir("0 - Sair");
            ui.imprimir("");
            opcao = ui.ler("Escolha uma opção: ");
            switch (opcao) {
                case "1" -> alunos.abrir();
                case "2" -> professores.abrir();
                case "3" -> disciplinas.abrir();
                case "4" -> matriculas.abrir();
                case "0" -> {
                }
                default -> ui.imprimir("Opção inválida.");
            }
        } while (!"0".equals(opcao));
    }
}
