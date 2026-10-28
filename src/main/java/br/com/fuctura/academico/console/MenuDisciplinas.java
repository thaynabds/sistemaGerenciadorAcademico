package br.com.fuctura.academico.console;

import br.com.fuctura.academico.controlador.DisciplinaControlador;
import br.com.fuctura.academico.controlador.ProfessorControlador;
import br.com.fuctura.academico.entity.Disciplina;

public final class MenuDisciplinas {

    private final ConsoleUI ui;
    private final DisciplinaControlador disciplinas;
    private final ProfessorControlador professores;

    public MenuDisciplinas(ConsoleUI ui, DisciplinaControlador disciplinas, ProfessorControlador professores) {
        this.ui = ui;
        this.disciplinas = disciplinas;
        this.professores = professores;
    }

    public void abrir() {
        String opcao;
        do {
            ui.imprimir("");
            ui.imprimir("=================================");
            ui.imprimir("        DISCIPLINAS");
            ui.imprimir("=================================");
            ui.imprimir("");
            ui.imprimir("1 - Cadastrar disciplina");
            ui.imprimir("2 - Listar disciplinas");
            ui.imprimir("3 - Buscar disciplina");
            ui.imprimir("4 - Alterar disciplina");
            ui.imprimir("5 - Excluir disciplina");
            ui.imprimir("0 - Voltar");
            ui.imprimir("");
            opcao = ui.ler("Escolha uma opção: ");
            switch (opcao) {
                case "1" -> ui.executar(this::cadastrar);
                case "2" -> ui.executar(this::listar);
                case "3" -> ui.executar(this::buscar);
                case "4" -> ui.executar(this::alterar);
                case "5" -> ui.executar(this::excluir);
                case "0" -> {
                }
                default -> ui.imprimir("Opção inválida.");
            }
        } while (!"0".equals(opcao));
    }

    private void cadastrar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("    CADASTRAR DISCIPLINA");
        ui.imprimir("=================================");
        ui.imprimir("");
        var lista = professores.listar();
        if (lista.isEmpty()) {
            ui.imprimir("Cadastre um professor antes de criar uma disciplina.");
            return;
        }
        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo(ui.ler("Código: "));
        disciplina.setNome(ui.ler("Nome: "));
        disciplina.setCargaHoraria(ui.ler("Carga Horária: "));
        ui.imprimir("");
        ui.imprimir("Professor responsável:");
        ui.imprimir("");
        lista.forEach(professor -> ui.imprimir(professor.getMatricula() + " - " + professor.getNome()));
        ui.imprimir("");
        disciplina.setProfessorMatricula(ui.ler("Matrícula do professor: "));
        disciplinas.cadastrar(disciplina);
        ui.imprimir("");
        ui.imprimir("Disciplina cadastrada com sucesso!");
    }

    private void alterar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("     ALTERAR DISCIPLINA");
        ui.imprimir("=================================");
        ui.imprimir("");
        Disciplina disciplina = disciplinas.buscar(ui.ler("Código: "));
        ui.imprimir("");
        ui.imprimir("Nome atual: " + disciplina.getNome());
        ui.imprimir("Carga horária atual: " + disciplina.getCargaHoraria());
        ui.imprimir("");
        disciplina.setNome(ui.ler("Novo nome: "));
        disciplina.setCargaHoraria(ui.ler("Nova carga horária: "));
        var lista = professores.listar();
        lista.forEach(professor -> ui.imprimir(professor.getMatricula() + " - " + professor.getNome()));
        ui.imprimir("");
        disciplina.setProfessorMatricula(ui.ler("Matrícula do professor: "));
        disciplinas.alterar(disciplina);
        ui.imprimir("");
        ui.imprimir("Disciplina alterada com sucesso!");
    }

    private void listar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      LISTA DE DISCIPLINAS");
        ui.imprimir("=================================");
        ui.imprimir("");
        var lista = disciplinas.listar();
        if (lista.isEmpty()) {
            ui.imprimir("Nenhuma disciplina cadastrada.");
        } else {
            lista.forEach(disciplina -> ui.imprimir(disciplina.getCodigo() + " - " + disciplina.getNome()));
        }
        ui.imprimir("");
        ui.pausar();
    }

    private void buscar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      BUSCAR DISCIPLINA");
        ui.imprimir("=================================");
        ui.imprimir("");
        Disciplina disciplina = disciplinas.buscar(ui.ler("Código: "));
        String professor = disciplina.getProfessorNome() == null ? "Não informado" : disciplina.getProfessorNome();
        ui.imprimir("");
        ui.imprimir("Dados encontrados:");
        ui.imprimir("");
        ui.imprimir("Código: " + disciplina.getCodigo());
        ui.imprimir("Nome: " + disciplina.getNome());
        ui.imprimir("Carga Horária: " + disciplina.getCargaHoraria());
        ui.imprimir("Professor: " + professor);
    }

    private void excluir() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("     EXCLUIR DISCIPLINA");
        ui.imprimir("=================================");
        ui.imprimir("");
        disciplinas.excluir(ui.ler("Código: "));
        ui.imprimir("");
        ui.imprimir("Disciplina removida com sucesso!");
    }
}
