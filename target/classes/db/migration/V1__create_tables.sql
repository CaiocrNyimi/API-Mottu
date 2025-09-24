CREATE TABLE patio (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    capacidade INT NOT NULL,
    vagas_disponiveis INT NOT NULL
);

CREATE TABLE vaga (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    identificador VARCHAR(255),
    ocupada BOOLEAN NOT NULL,
    codigo VARCHAR(255) NOT NULL,
    patio_id BIGINT NOT NULL,
    FOREIGN KEY (patio_id) REFERENCES patio(id)
);

CREATE TABLE app_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    CHECK (role IN ('ADMIN', 'CLIENTE'))
);

CREATE TABLE moto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    placa VARCHAR(255) NOT NULL UNIQUE,
    modelo VARCHAR(255) NOT NULL,
    ano INT NOT NULL,
    quilometragem INT NOT NULL,
    status VARCHAR(255) NOT NULL,
    patio_id BIGINT,
    vaga_id BIGINT,
    FOREIGN KEY (patio_id) REFERENCES patio(id),
    FOREIGN KEY (vaga_id) REFERENCES vaga(id),
    CHECK (modelo IN ('MOTTU_SPORT', 'MOTTU_E', 'MOTTU_POP'))
);

CREATE TABLE aluguel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    status VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    moto_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (moto_id) REFERENCES moto(id)
);

CREATE TABLE manutencao (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    data_manutencao DATETIME NOT NULL,
    tipo_servico VARCHAR(255) NOT NULL,
    quilometragem INT NOT NULL,
    moto_id BIGINT NOT NULL,
    FOREIGN KEY (moto_id) REFERENCES moto(id)
);