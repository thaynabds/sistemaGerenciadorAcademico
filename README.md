# Sistema Acadêmico

Projeto de conclusão do módulo de Java com Banco de Dados da Fuctura (turma J2 2026.2).

## Descrição

Aplicação Java executada no console para controle acadêmico. Permite cadastrar e gerenciar **alunos**, **professores**, **disciplinas** e **matrículas**, com os dados guardados no PostgreSQL e acessados por JDBC.

O enunciado está em [javajdk2023/j2 - projeto.md](https://github.com/javajdk2023/j2/blob/main/projeto.md).

### Funcionalidades

| Menu | Opções |
|---|---|
| Alunos | Cadastrar, listar (ordenado por nome), buscar, alterar, excluir, pesquisar por nome |
| Professores | Cadastrar, listar, buscar, alterar, excluir |
| Disciplinas | Cadastrar (vinculada a um professor), listar, buscar, alterar, excluir |
| Matrículas | Matricular aluno, cancelar matrícula, disciplinas do aluno, alunos da disciplina, situação acadêmica do aluno, relatório completo |

Regras aplicadas na camada `service`:

- matrícula, código e campos obrigatórios não podem ficar vazios;
- não aceita aluno, professor ou disciplina com chave repetida;
- a disciplina precisa de um professor existente;
- a carga horária precisa ser um número inteiro positivo;
- o aluno não pode ser matriculado duas vezes na mesma disciplina;
- não exclui aluno com matrícula, professor com disciplina ou disciplina com alunos matriculados (mostra uma mensagem explicando o motivo).

## Tecnologias

- Java 21
- Maven (o projeto inclui o Maven Wrapper `mvnw`)
- PostgreSQL
- JDBC (`DriverManager`, `Connection`, `PreparedStatement`, `ResultSet`, `SQLException`) com o driver [PostgreSQL JDBC](https://jdbc.postgresql.org/)
- [JLine 3](https://github.com/jline/jline3) para a interação no terminal
- [Lombok](https://projectlombok.org/) para getters e setters das entidades
- Eclipse IDE

## Arquitetura

```text
src/main/java/br/com/fuctura/academico
├── application      Application.java (inicialização)
├── console          ConsoleUI, MenuPrincipal e um menu para cada entidade
├── controlador      AlunoControlador, ProfessorControlador, DisciplinaControlador, MatriculaControlador
├── entity           Aluno, Professor, Disciplina, Matricula, SituacaoAcademica
├── service          AlunoService, ProfessorService, DisciplinaService, MatriculaService
├── dao              AlunoDAO, ProfessorDAO, DisciplinaDAO, MatriculaDAO
└── infrastructure   DatabaseConfig, ConnectionFactory, InicializadorBanco
```

Fluxo de uma operação:

```text
Usuário → Console → Controlador → Service → DAO → JDBC → PostgreSQL
```

O controlador nunca acessa o banco. Toda operação de banco fica no DAO e usa `PreparedStatement`. As conexões são criadas apenas pela `ConnectionFactory`.

### Banco de dados

```text
PROFESSOR 1 ──── N DISCIPLINA 1 ──── N MATRICULA N ──── 1 ALUNO
```

| Tabela | Chave primária | Chaves estrangeiras |
|---|---|---|
| `aluno` | `matricula` | |
| `professor` | `matricula` | |
| `disciplina` | `codigo` | `professor_matricula` → `professor` |
| `matricula` | `id` | `aluno_matricula` → `aluno`, `disciplina_codigo` → `disciplina` |

Consultas com `JOIN`:

- **Situação acadêmica** e **relatório completo**: `matricula` + `aluno` + `disciplina` + `professor` (4 tabelas), mostrando aluno, disciplina, professor e data da matrícula;
- **Disciplinas do aluno**: `matricula` + `disciplina`;
- **Alunos da disciplina**: `matricula` + `aluno`;
- **Listar/buscar disciplina**: `disciplina` + `professor`.

## Configurar o banco

1. Instale o PostgreSQL (porta padrão `5432`, usuário `postgres`).
2. Crie o banco:

   ```sql
   CREATE DATABASE academico;
   ```

3. Conectado ao banco `academico` (DBeaver, pgAdmin ou `psql`), execute o arquivo [`database.sql`](database.sql). Ele cria as tabelas, as chaves primárias e estrangeiras, e insere dados iniciais para teste:

   ```bash
   psql -U postgres -d academico -f database.sql
   ```

A aplicação também cria as tabelas automaticamente ao iniciar (script `src/main/resources/db/schema.sql`), então o passo 3 é necessário apenas para carregar os dados de exemplo.

## Configurar a aplicação

Dados de conexão com o PostgreSQL:

| Campo | Valor padrão |
|---|---|
| URL JDBC | `jdbc:postgresql://localhost:5432/academico` |
| Usuário | `postgres` |
| Senha | a senha definida na instalação do PostgreSQL |

1. Copie `database.properties.example` para `database.properties` na raiz do projeto.
2. Preencha `db.password` (e altere `db.url` e `db.user` se o seu banco for diferente).

```properties
db.url=jdbc:postgresql://localhost:5432/academico
db.user=postgres
db.password=sua_senha
```

O arquivo `database.properties` está no `.gitignore` e não vai para o GitHub. Também é possível usar variáveis de ambiente, que têm prioridade sobre o arquivo: `DB_URL`, `DB_USER` e `DB_PASSWORD`.

Para usar o PostgreSQL no [Neon](https://neon.tech/) em vez do local, use as linhas comentadas do `database.properties.example`.

## Executar

### Pelo Eclipse

1. `File > Import > Maven > Existing Maven Projects` e selecione a pasta do projeto.
2. Instale o Lombok no Eclipse, se ainda não tiver (execute o `lombok.jar` e aponte para o Eclipse).
3. Crie o `database.properties` como explicado acima.
4. Abra `br.com.fuctura.academico.application.Application` e use `Run As > Java Application`.

### Pelo terminal

```bash
./mvnw -q compile exec:java
```

No Windows use `mvnw.cmd -q compile exec:java`. Com o Maven instalado também funciona `mvn -q compile exec:java`.

Para gerar um JAR executável:

```bash
./mvnw -q package
java -jar target/sistema-academico-1.0-SNAPSHOT.jar
```

### Menu principal

```text
=================================
     SISTEMA ACADÊMICO
=================================

1 - Alunos
2 - Professores
3 - Disciplinas
4 - Matrículas
0 - Sair

Escolha uma opção:
```

## Autora

Thayná Batista da Silva - [github.com/thaynabds](https://github.com/thaynabds)
