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
                    new AlunoControlador(new AlunoService(alunoDao)),
                    new ProfessorControlador(new ProfessorService(professorDao)),
                    new DisciplinaControlador(new DisciplinaService(disciplinaDao, professorDao)),
                    new MatriculaControlador(new MatriculaService(alunoDao, disciplinaDao, matriculaDao)));
            menu.abrir();
        }
    }
}
