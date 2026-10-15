# Sistema Acadêmico

Aplicação de console em Java para cadastro de alunos, professores, disciplinas e matrículas. O acesso ao PostgreSQL é feito com JDBC.

O enunciado da turma está em [javajdk2023/j2](https://github.com/javajdk2023/j2/blob/main/projeto.md).

## Tecnologias

- Java 21
- Maven
- PostgreSQL no [Neon](https://neon.tech/docs/introduction)
- JDBC, driver [PostgreSQL JDBC](https://jdbc.postgresql.org/documentation/use/)
- [JLine 3](https://github.com/jline/jline3)
- [Lombok](https://projectlombok.org/features/GetterSetter)

## Camadas

Console chama o controlador. O controlador chama o service. O service aplica as regras e chama o DAO. O DAO usa `PreparedStatement`. A conexão fica em `ConnectionFactory`.

Pacote base: `br.com.fuctura.academico`.

## Banco

1. Copie `database.properties.example` para `database.properties`.
2. Preencha `db.password`. A senha também pode ir na variável `DB_PASSWORD`.
3. O arquivo `database.properties` não entra no Git.

O script de criação das tabelas está em `database.sql`. Ao iniciar, a aplicação também aplica `src/main/resources/db/schema.sql`.

## Execução

```bash
mvn -q exec:java
```

Classe principal: `br.com.fuctura.academico.application.Application`.
