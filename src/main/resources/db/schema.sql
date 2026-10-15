CREATE TABLE IF NOT EXISTS aluno (
    matricula VARCHAR(255) PRIMARY KEY,
    nome VARCHAR(100),
    idade INT,
    celular VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS professor (
    nome VARCHAR(100),
    telefone VARCHAR(15),
    matricula VARCHAR(32) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS disciplina (
    nome VARCHAR(100),
    carga_horaria VARCHAR(15),
    codigo VARCHAR(32) PRIMARY KEY
);

ALTER TABLE disciplina ADD COLUMN IF NOT EXISTS professor_matricula VARCHAR(32);

ALTER TABLE disciplina DROP CONSTRAINT IF EXISTS fk_disciplina_professor;

ALTER TABLE disciplina
    ADD CONSTRAINT fk_disciplina_professor
    FOREIGN KEY (professor_matricula)
    REFERENCES professor (matricula);

CREATE TABLE IF NOT EXISTS matricula (
    id SERIAL PRIMARY KEY,
    data_matricula DATE,
    aluno_matricula VARCHAR(255),
    disciplina_codigo VARCHAR(32)
);

ALTER TABLE matricula DROP CONSTRAINT IF EXISTS fk_matricula_aluno;

ALTER TABLE matricula
    ADD CONSTRAINT fk_matricula_aluno
    FOREIGN KEY (aluno_matricula)
    REFERENCES aluno (matricula);

ALTER TABLE matricula DROP CONSTRAINT IF EXISTS fk_matricula_disciplina;

ALTER TABLE matricula
    ADD CONSTRAINT fk_matricula_disciplina
    FOREIGN KEY (disciplina_codigo)
    REFERENCES disciplina (codigo);
