package br.com.fuctura.academico.console;

import br.com.fuctura.academico.controlador.AlunoControlador;
import br.com.fuctura.academico.entity.Aluno;

public final class MenuAlunos {

    private final ConsoleUI ui;
    private final AlunoControlador alunos;

    public MenuAlunos(ConsoleUI ui, AlunoControlador alunos) {
        this.ui = ui;
        this.alunos = alunos;
    }

    public void abrir() {
        String opcao;
        do {
            ui.imprimir("");
            ui.imprimir("=================================");
            ui.imprimir("          ALUNOS");
            ui.imprimir("=================================");
            ui.imprimir("");
            ui.imprimir("1 - Cadastrar");
            ui.imprimir("2 - Listar");
            ui.imprimir("3 - Buscar");
            ui.imprimir("4 - Alterar");
            ui.imprimir("5 - Excluir");
            ui.imprimir("6 - Pesquisar por nome");
            ui.imprimir("0 - Voltar");
            ui.imprimir("");
            opcao = ui.ler("Escolha: ");
            switch (opcao) {
                case "1" -> ui.executar(this::cadastrar);
                case "2" -> ui.executar(this::listar);
                case "3" -> ui.executar(this::buscar);
                case "4" -> ui.executar(this::alterar);
                case "5" -> ui.executar(this::excluir);
                case "6" -> ui.executar(this::pesquisarPorNome);
                case "0" -> {
                }
                default -> ui.imprimir("Opção inválida.");
            }
        } while (!"0".equals(opcao));
    }

    private void cadastrar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("      CADASTRAR ALUNO");
        ui.imprimir("=================================");
        ui.imprimir("");
        Aluno aluno = new Aluno();
        aluno.setMatricula(ui.ler("Matrícula: "));
        aluno.setNome(ui.ler("Nome: "));
        aluno.setIdade(lerIdade());
        aluno.setCelular(ui.ler("Celular: "));
        alunos.cadastrar(aluno);
        ui.imprimir("");
        ui.imprimir("Aluno cadastrado com sucesso!");
    }

    private void alterar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("        ALTERAR ALUNO");
        ui.imprimir("=================================");
        ui.imprimir("");
        Aluno aluno = alunos.buscar(ui.ler("Matrícula: "));
        ui.imprimir("");
        ui.imprimir("Nome atual: " + aluno.getNome());
        ui.imprimir("Idade atual: " + aluno.getIdade());
        ui.imprimir("Celular atual: " + aluno.getCelular());
        ui.imprimir("");
        aluno.setNome(ui.ler("Novo nome: "));
        aluno.setIdade(lerIdade());
        aluno.setCelular(ui.ler("Novo celular: "));
        alunos.alterar(aluno);
        ui.imprimir("");
        ui.imprimir("Aluno alterado com sucesso!");
    }

    private void listar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("        LISTA DE ALUNOS");
        ui.imprimir("=================================");
        ui.imprimir("");
        var lista = alunos.listar();
        if (lista.isEmpty()) {
            ui.imprimir("Nenhum aluno cadastrado.");
        } else {
            lista.forEach(aluno -> ui.imprimir(aluno.getMatricula() + " - " + aluno.getNome()));
        }
        ui.imprimir("");
        ui.pausar();
    }

    private void buscar() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("        BUSCAR ALUNO");
        ui.imprimir("=================================");
        ui.imprimir("");
        mostrar(alunos.buscar(ui.ler("Matrícula: ")));
    }

    private void pesquisarPorNome() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("     PESQUISAR POR NOME");
        ui.imprimir("=================================");
        ui.imprimir("");
        var lista = alunos.buscarPorNome(ui.ler("Nome: "));
        ui.imprimir("");
        if (lista.isEmpty()) {
            ui.imprimir("Nenhum aluno encontrado.");
        } else {
            lista.forEach(this::mostrar);
        }
    }

    private void excluir() {
        ui.imprimir("");
        ui.imprimir("=================================");
        ui.imprimir("       EXCLUIR ALUNO");
        ui.imprimir("=================================");
        ui.imprimir("");
        alunos.excluir(ui.ler("Matrícula: "));
        ui.imprimir("");
        ui.imprimir("Aluno removido com sucesso!");
    }

    private void mostrar(Aluno aluno) {
        ui.imprimir("");
        ui.imprimir("Matrícula: " + aluno.getMatricula());
        ui.imprimir("Nome: " + aluno.getNome());
        ui.imprimir("Idade: " + aluno.getIdade());
        ui.imprimir("Celular: " + aluno.getCelular());
    }

    private int lerIdade() {
        String texto = ui.ler("Idade: ");
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe uma idade numérica válida.");
        }
    }
}
