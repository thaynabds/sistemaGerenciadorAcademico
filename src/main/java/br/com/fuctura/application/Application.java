package br.com.fuctura.application;

import br.com.fuctura.console.ConsoleUI;
import br.com.fuctura.console.MenuPrincipal;
import br.com.fuctura.dao.AlunoDAO;
import br.com.fuctura.dao.DisciplinaDAO;
import br.com.fuctura.dao.MatriculaDAO;
import br.com.fuctura.dao.ProfessorDAO;
import br.com.fuctura.infrastructure.ConnectionFactory;
import br.com.fuctura.infrastructure.DatabaseConfig;
import br.com.fuctura.infrastructure.InicializadorBanco;
import br.com.fuctura.service.AlunoService;
import br.com.fuctura.service.DisciplinaService;
import br.com.fuctura.service.MatriculaService;
import br.com.fuctura.service.ProfessorService;

public final class Application {

    private Application() {
    }

    public static void main(String[] args) {
        DatabaseConfig config = DatabaseConfig.carregar();
        ConnectionFactory conexoes = new ConnectionFactory(config);
        new InicializadorBanco(conexoes).aplicar();

        AlunoDAO alunoDao = new AlunoDAO(conexoes);
        ProfessorDAO professorDao = new ProfessorDAO(conexoes);
        DisciplinaDAO disciplinaDao = new DisciplinaDAO(conexoes);
        MatriculaDAO matriculaDao = new MatriculaDAO(conexoes);

        try (ConsoleUI ui = new ConsoleUI()) {
            MenuPrincipal menu = new MenuPrincipal(
                    ui,
                    new AlunoService(alunoDao),
                    new ProfessorService(professorDao),
                    new DisciplinaService(disciplinaDao, professorDao),
                    new MatriculaService(alunoDao, disciplinaDao, matriculaDao));
            menu.abrir();
        }
    }
}
