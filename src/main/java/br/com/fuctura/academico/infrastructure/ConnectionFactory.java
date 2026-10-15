package br.com.fuctura.academico.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private final DatabaseConfig config;

    public ConnectionFactory(DatabaseConfig config) {
        this.config = config;
    }

    public Connection abrir() {
        try {
            return DriverManager.getConnection(config.getUrl(), config.getUsuario(), config.getSenha());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
