package br.com.fuctura.academico.console;

import br.com.fuctura.academico.controlador.ProfessorControlador;
import br.com.fuctura.academico.entity.Professor;

public final class MenuProfessores {

    private final ConsoleUI ui;
    private final ProfessorControlador professores;

    public MenuProfessores(ConsoleUI ui, ProfessorControlador professores) {
        this.ui = ui;
        this.professores = professores;
    }

    public void abrir() {
        String opcao;
        do {
            ui.imprimir("");
            ui.imprimir("=================================");
            ui.imprimir("        PROFESSORES");
            ui.imprimir("=================================");
            ui.imprimir("");
            ui.imprimir("1 - Cadastrar");
            ui.imprimir("2 - Listar");
            ui.imprimir("3 - Buscar");
            ui.imprimir("4 - Alterar");
            ui.imprimir("5 - Excluir");
            ui.imprimir("0 - Voltar");
            ui.imprimir("");
            opcao = ui.ler("Escolha: ");
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
        ui.imprimir("    CADASTRAR PROFESSOR");
        ui.imprimir("=================================");
        ui.imprimir("");
        Professor professor = new Professor();
        professor.setMatricula(ui.ler("Matrícula: "));
        professor.setNome(ui.ler("Nome: "));
        professor.setTelefone(ui.ler("Telefone: "));
        professores.cadastrar(professor);
        ui.imprimir("");
        ui.imprimir("Professor cadastrado com sucesso!");
    }

    private void alterar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      ALTERAR PROFESSOR");
        ui.imprimir("=================================");
        ui.imprimir("");
        Professor professor = professores.buscar(ui.ler("Matrícula: "));
        ui.imprimir("");
        ui.imprimir("Nome atual: " + professor.getNome());
        ui.imprimir("Telefone atual: " + professor.getTelefone());
        ui.imprimir("");
        professor.setNome(ui.ler("Novo nome: "));
        professor.setTelefone(ui.ler("Novo telefone: "));
        professores.alterar(professor);
        ui.imprimir("");
        ui.imprimir("Professor alterado com sucesso!");
    }

    private void listar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("     LISTA DE PROFESSORES");
        ui.imprimir("=================================");
        ui.imprimir("");
        var lista = professores.listar();
        if (lista.isEmpty()) {
            ui.imprimir("Nenhum professor cadastrado.");
        } else {
            lista.forEach(professor -> ui.imprimir(professor.getMatricula() + " - " + professor.getNome()));
        }
        ui.imprimir("");
        ui.pausar();
    }

    private void buscar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      BUSCAR PROFESSOR");
        ui.imprimir("=================================");
        ui.imprimir("");
        Professor professor = professores.buscar(ui.ler("Matrícula: "));
        ui.imprimir("");
        ui.imprimir("Dados encontrados:");
        ui.imprimir("");
        ui.imprimir("Matrícula: " + professor.getMatricula());
        ui.imprimir("Nome: " + professor.getNome());
        ui.imprimir("Telefone: " + professor.getTelefone());
    }

    private void excluir() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      EXCLUIR PROFESSOR");
        ui.imprimir("=================================");
        ui.imprimir("");
        professores.excluir(ui.ler("Matrícula: "));
        ui.imprimir("");
        ui.imprimir("Professor removido com sucesso!");
    }
}
