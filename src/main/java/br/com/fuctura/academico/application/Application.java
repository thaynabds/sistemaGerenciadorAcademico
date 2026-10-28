package br.com.fuctura.academico.application;

import br.com.fuctura.academico.console.ConsoleUI;
import br.com.fuctura.academico.console.MenuPrincipal;
import br.com.fuctura.academico.controlador.AlunoControlador;
import br.com.fuctura.academico.controlador.DisciplinaControlador;
import br.com.fuctura.academico.controlador.MatriculaControlador;
import br.com.fuctura.academico.controlador.ProfessorControlador;
import br.com.fuctura.academico.dao.AlunoDAO;
import br.com.fuctura.academico.dao.DisciplinaDAO;
import br.com.fuctura.academico.dao.MatriculaDAO;
import br.com.fuctura.academico.dao.ProfessorDAO;
import br.com.fuctura.academico.infrastructure.ConnectionFactory;
import br.com.fuctura.academico.infrastructure.DatabaseConfig;
import br.com.fuctura.academico.infrastructure.InicializadorBanco;
import br.com.fuctura.academico.service.AlunoService;
import br.com.fuctura.academico.service.DisciplinaService;
import br.com.fuctura.academico.service.MatriculaService;
import br.com.fuctura.academico.service.ProfessorService;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) throws InterruptedException {
        String porta = System.getenv("PORT");
        if (porta != null && !porta.isBlank()) {
            subirHttp(Integer.parseInt(porta.trim()));
            return;
        }
        abrirConsole();
    }

    private static void subirHttp(int porta) throws InterruptedException {
        prepararBanco();
        try {
            var servidor = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress("0.0.0.0", porta), 0);
            servidor.createContext("/", troca -> {
                byte[] corpo = """
                        {"status":"ok","servico":"sistema gerenciador academico","mensagem":"Deploy de portfolio com H2 em memoria. O uso completo continua no console Java."}
                        """.trim().getBytes(StandardCharsets.UTF_8);
                troca.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
                troca.sendResponseHeaders(200, corpo.length);
                troca.getResponseBody().write(corpo);
                troca.getResponseBody().close();
            });
            servidor.start();
            new CountDownLatch(1).await();
        } catch (Exception e) {
            throw new IllegalStateException("Nao foi possivel publicar a pagina do portfolio.", e);
        }
    }

    private static void abrirConsole() {
        prepararBanco();
        AlunoDAO alunoDao = new AlunoDAO(conexoes());
        ProfessorDAO professorDao = new ProfessorDAO(conexoes());
        DisciplinaDAO disciplinaDao = new DisciplinaDAO(conexoes());
        MatriculaDAO matriculaDao = new MatriculaDAO(conexoes());

        try (ConsoleUI ui = new ConsoleUI()) {
            MenuPrincipal menu = new MenuPrincipal(
                    ui,
                    new AlunoControlador(new AlunoService(alunoDao, matriculaDao)),
                    new ProfessorControlador(new ProfessorService(professorDao, disciplinaDao)),
                    new DisciplinaControlador(new DisciplinaService(disciplinaDao, professorDao, matriculaDao)),
                    new MatriculaControlador(new MatriculaService(alunoDao, disciplinaDao, matriculaDao)));
            menu.abrir();
        }
    }

    private static ConnectionFactory conexoes() {
        return new ConnectionFactory(DatabaseConfig.carregar());
    }

    private static void prepararBanco() {
        new InicializadorBanco(conexoes()).aplicar();
    }
}
