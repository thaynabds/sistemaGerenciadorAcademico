package br.com.fuctura.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseFactory {

    private DatabaseFactory() {
    }

    public static Connection getConnection() {
        DatabaseConfig config = DatabaseConfig.carregar();
        try {
            return DriverManager.getConnection(config.getUrl(), config.getUsuario(), config.getSenha());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
