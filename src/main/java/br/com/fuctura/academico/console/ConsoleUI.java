package br.com.fuctura.academico.console;

import java.io.Closeable;
import java.io.IOException;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public final class ConsoleUI implements Closeable {

    private final Terminal terminal;
    private final LineReader leitor;

    public ConsoleUI() {
        try {
            boolean interativo = System.console() != null;
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .dumb(!interativo)
                    .build();
            this.leitor = LineReaderBuilder.builder().terminal(terminal).build();
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível abrir o terminal.", e);
        }
    }

    public void imprimir(String texto) {
        terminal.writer().println(texto);
        terminal.writer().flush();
    }

    public String ler(String prompt) {
        try {
            String linha = leitor.readLine(prompt);
            return linha == null ? "0" : linha.trim();
        } catch (UserInterruptException | EndOfFileException e) {
            return "0";
        }
    }

    public void pausar() {
        ler("Pressione ENTER para continuar...");
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
        try {
            terminal.close();
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível fechar o terminal.", e);
        }
    }
}
