package br.com.fuctura.console;

import java.io.Closeable;
import java.util.Scanner;

public final class ConsoleUI implements Closeable {

    private final Scanner leitor;

    public ConsoleUI() {
        this.leitor = new Scanner(System.in);
    }

    public void imprimir(String texto) {
        System.out.println(texto);
    }

    public String ler(String prompt) {
        System.out.print(prompt);
        if (!leitor.hasNextLine()) {
            return "0";
        }
        return leitor.nextLine().trim();
    }

    public void pausar() {
        System.out.print("Pressione ENTER para continuar...");
        if (leitor.hasNextLine()) {
            leitor.nextLine();
        }
    }

    public void executar(Runnable acao) {
        try {
            acao.run();
        } catch (RuntimeException e) {
            imprimir(e.getMessage());
        }
    }

    @Override
    public void close() {
        leitor.close();
    }
}
