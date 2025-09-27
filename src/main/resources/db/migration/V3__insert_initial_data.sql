INSERT INTO patio (nome, endereco, capacidade, vagas_disponiveis) VALUES ('Mottu Pátio Central', 'Avenida Paulista, 1000', 20, 20);

INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A01', TRUE, 'A01', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A02', TRUE, 'A02', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A03', FALSE, 'A03', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A04', FALSE, 'A04', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A05', FALSE, 'A05', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A06', FALSE, 'A06', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A07', FALSE, 'A07', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A08', FALSE, 'A08', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A09', FALSE, 'A09', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-A10', FALSE, 'A10', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B01', FALSE, 'B01', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B02', FALSE, 'B02', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B03', FALSE, 'B03', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B04', FALSE, 'B04', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B05', FALSE, 'B05', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B06', FALSE, 'B06', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B07', FALSE, 'B07', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B08', FALSE, 'B08', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B09', FALSE, 'B09', 1);
INSERT INTO vaga (identificador, ocupada, codigo, patio_id) VALUES ('VAGA-B10', FALSE, 'B10', 1);

INSERT INTO moto (placa, modelo, ano, quilometragem, status, patio_id, vaga_id) VALUES ('ABC-1234', 'MOTTU_SPORT', 2022, 10000, 'DISPONIVEL', 1, 1);
INSERT INTO moto (placa, modelo, ano, quilometragem, status, patio_id, vaga_id) VALUES ('XYZ-5678', 'MOTTU_E', 2023, 5000, 'ALUGADA', 1, 2);