package br.com.fuctura.academico.infrastructure;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;

public final class DatabaseConfig {

    private final String url;
    private final String usuario;
    private final String senha;

    public DatabaseConfig(String url, String usuario, String senha) {
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    public static DatabaseConfig carregar() {
        Properties arquivo = new Properties();
        Path local = Path.of("database.properties");
        if (Files.exists(local)) {
            try (Reader leitor = Files.newBufferedReader(local, StandardCharsets.UTF_8)) {
                arquivo.load(leitor);
            } catch (IOException e) {
                throw new IllegalStateException("Não foi possível ler database.properties.", e);
            }
        }
        return montar(System.getenv(), arquivo);
    }

    static DatabaseConfig montar(Map<String, String> ambiente, Properties arquivo) {
        String perfil = primeiroInformado(ambiente.get("DB_PROFILE"), arquivo.getProperty("db.profile"), "");
        if ("h2".equalsIgnoreCase(perfil)) {
            try {
                Class.forName("org.h2.Driver");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Driver H2 ausente no artefato.", e);
            }
            return new DatabaseConfig(
                    "jdbc:h2:mem:academico;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1",
                    "sa",
                    "");
        }
        String url = primeiroInformado(
                ambiente.get("DB_URL"),
                arquivo.getProperty("db.url"),
                "jdbc:postgresql://ep-odd-recipe-aeoa1pqy-pooler.c-2.us-east-2.aws.neon.tech/neondb?sslmode=require&channelBinding=require");
        String usuario = primeiroInformado(
                ambiente.get("DB_USER"),
                arquivo.getProperty("db.user"),
                "neondb_owner");
        String senha = primeiroInformado(ambiente.get("DB_PASSWORD"), arquivo.getProperty("db.password"), "");
        if (senha.isBlank()) {
            throw new IllegalStateException(
                    "Senha do PostgreSQL não configurada. Copie database.properties.example para database.properties e preencha db.password, ou defina a variável DB_PASSWORD.");
        }
        return new DatabaseConfig(url, usuario, senha);
    }

    private static String primeiroInformado(String ambiente, String arquivo, String padrao) {
        if (ambiente != null && !ambiente.isBlank()) {
            return ambiente.trim();
        }
        if (arquivo != null && !arquivo.isBlank()) {
            return arquivo.trim();
        }
        return padrao;
    }

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }
}
