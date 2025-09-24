INSERT INTO patio (nome, endereco, capacidade, vagas_disponiveis)
VALUES ('Mottu Pátio Central', 'Avenida Paulista, 1000', 5, 5);

INSERT INTO vaga (identificador, ocupada, codigo, patio_id)
VALUES ('VAGA-A01', TRUE, 'A01', 1);

INSERT INTO vaga (identificador, ocupada, codigo, patio_id)
VALUES ('VAGA-A02', FALSE, 'A02', 1);

INSERT INTO vaga (identificador, ocupada, codigo, patio_id)
VALUES ('VAGA-A03', FALSE, 'A03', 1);

INSERT INTO vaga (identificador, ocupada, codigo, patio_id)
VALUES ('VAGA-A04', FALSE, 'A04', 1);

INSERT INTO vaga (identificador, ocupada, codigo, patio_id)
VALUES ('VAGA-A05', FALSE, 'A05', 1);

INSERT INTO moto (placa, modelo, ano, quilometragem, status, patio_id, vaga_id)
VALUES ('ABC-1234', 'MOTTU_SPORT', 2022, 10000, 'DISPONIVEL', 1, 1);

INSERT INTO moto (placa, modelo, ano, quilometragem, status, patio_id, vaga_id)
VALUES ('XYZ-5678', 'MOTTU_E', 2023, 5000, 'ALUGADA', 1, 2);