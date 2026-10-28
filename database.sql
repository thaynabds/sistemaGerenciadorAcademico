-- =========================================================
-- Sistema Acadêmico - script de criação do banco (PostgreSQL)
--
-- Como usar:
--   1. CREATE DATABASE academico;
--   2. Conecte no banco "academico" e execute este arquivo
--      (DBeaver, pgAdmin ou: psql -U postgres -d academico -f database.sql)
--
-- O script pode ser executado mais de uma vez sem erro.
-- =========================================================

-- ---------------------------------------------------------
-- Tabelas
-- ---------------------------------------------------------

CREATE TABLE IF NOT EXISTS aluno (
    matricula VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    celular VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS professor (
    matricula VARCHAR(32) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL
);

-- PROFESSOR 1:N DISCIPLINA
CREATE TABLE IF NOT EXISTS disciplina (
    codigo VARCHAR(32) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria VARCHAR(15) NOT NULL,
    professor_matricula VARCHAR(32) NOT NULL,
    CONSTRAINT fk_disciplina_professor
        FOREIGN KEY (professor_matricula)
        REFERENCES professor (matricula)
);

-- ALUNO N:N DISCIPLINA (resolvido pela tabela matricula)
CREATE TABLE IF NOT EXISTS matricula (
    id SERIAL PRIMARY KEY,
    data_matricula DATE NOT NULL,
    aluno_matricula VARCHAR(255) NOT NULL,
    disciplina_codigo VARCHAR(32) NOT NULL,
    CONSTRAINT fk_matricula_aluno
        FOREIGN KEY (aluno_matricula)
        REFERENCES aluno (matricula),
    CONSTRAINT fk_matricula_disciplina
        FOREIGN KEY (disciplina_codigo)
        REFERENCES disciplina (codigo),
    CONSTRAINT uk_matricula_aluno_disciplina
        UNIQUE (aluno_matricula, disciplina_codigo)
);

-- ---------------------------------------------------------
-- Dados iniciais para teste
-- ---------------------------------------------------------

INSERT INTO professor (matricula, nome, telefone) VALUES
    ('P001', 'Carlos Andrade', '81999990001'),
    ('P002', 'Fernanda Lima', '81999990002')
ON CONFLICT (matricula) DO NOTHING;

INSERT INTO disciplina (codigo, nome, carga_horaria, professor_matricula) VALUES
    ('JAVA01', 'Programação Java', '80', 'P001'),
    ('BD01', 'Banco de Dados', '60', 'P002'),
    ('POO01', 'Orientação a Objetos', '40', 'P001')
ON CONFLICT (codigo) DO NOTHING;

INSERT INTO aluno (matricula, nome, idade, celular) VALUES
    ('A001', 'Ana Beatriz', 20, '81988880001'),
    ('A002', 'Bruno Costa', 23, '81988880002'),
    ('A003', 'Camila Souza', 19, '81988880003')
ON CONFLICT (matricula) DO NOTHING;

INSERT INTO matricula (data_matricula, aluno_matricula, disciplina_codigo) VALUES
    (CURRENT_DATE, 'A001', 'JAVA01'),
    (CURRENT_DATE, 'A001', 'BD01'),
    (CURRENT_DATE, 'A002', 'JAVA01'),
    (CURRENT_DATE, 'A003', 'POO01')
ON CONFLICT (aluno_matricula, disciplina_codigo) DO NOTHING;

-- ---------------------------------------------------------
-- Consultas de exemplo com JOIN
-- ---------------------------------------------------------

-- Disciplinas de um aluno (aluno + matricula + disciplina + professor: 4 tabelas)
-- SELECT a.nome AS aluno, d.nome AS disciplina, p.nome AS professor, m.data_matricula
-- FROM matricula m
-- INNER JOIN aluno a ON a.matricula = m.aluno_matricula
-- INNER JOIN disciplina d ON d.codigo = m.disciplina_codigo
-- INNER JOIN professor p ON p.matricula = d.professor_matricula
-- WHERE a.matricula = 'A001';

-- Alunos de uma disciplina
-- SELECT a.matricula, a.nome
-- FROM matricula m
-- INNER JOIN aluno a ON a.matricula = m.aluno_matricula
-- WHERE m.disciplina_codigo = 'JAVA01'
-- ORDER BY a.nome;
