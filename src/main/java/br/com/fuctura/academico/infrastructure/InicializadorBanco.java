package br.com.fuctura.academico.infrastructure;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class InicializadorBanco {

    private final ConnectionFactory conexoes;

    public InicializadorBanco(ConnectionFactory conexoes) {
        this.conexoes = conexoes;
    }

    public void aplicar() {
        String script = lerScript();
        try (Connection conexao = conexoes.abrir()) {
            for (String comando : script.split(";")) {
                String sql = comando.trim();
                if (sql.isEmpty()) {
                    continue;
                }
                try (Statement statement = conexao.createStatement()) {
                    statement.execute(sql);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String lerScript() {
        try (var entrada = InicializadorBanco.class.getResourceAsStream("/db/schema.sql")) {
            return new String(Objects.requireNonNull(entrada, "Script schema.sql ausente.").readAllBytes(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Não foi possível ler o script do banco.", e);
        }
    }
}
