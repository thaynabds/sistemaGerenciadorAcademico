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

CREATE TABLE IF NOT EXISTS disciplina (
    codigo VARCHAR(32) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria VARCHAR(15) NOT NULL,
    professor_matricula VARCHAR(32) NOT NULL,
    CONSTRAINT fk_disciplina_professor
        FOREIGN KEY (professor_matricula)
        REFERENCES professor (matricula)
);

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
        REFERENCES disciplina (codigo)
);
