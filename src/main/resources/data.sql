-- Inserting data into the usuario table
INSERT INTO usuario (cpf, nome, perfil, senha)
VALUES ('00000000000', 'Usuario A', 'ADMIN', '$2a$10$phC3QC1qhw4wjGS7e0gi.ec95i2AWOsYhKMCJN2EDBLO3wb1xgx4q'),
       ('00000000001', 'Usuario B', 'CLIENTE', '$2a$10$phC3QC1qhw4wjGS7e0gi.ec95i2AWOsYhKMCJN2EDBLO3wb1xgx4q');

-- Inserting data into the cliente table
INSERT INTO cliente (data_cadastro, cpf, nome)
VALUES ('2023-01-01', '00000000000', 'Cliente A'),
       ('2023-01-02', '00000000001', 'Cliente B');

-- Inserting data into the contato table
-- O campo cliente_cpf foi ajustado para refletir o relacionamento correto
INSERT INTO contato (cliente_cpf, valor, tag, tipo)
VALUES ('00000000000', 'Contato A', 'Tag A', 'Tipo A'),
       ('00000000000', 'Contato B', 'Tag B', 'Tipo B'),
       ('00000000001', 'Contato C', 'Tag C', 'Tipo C');

-- Inserting data into the endereco table
-- O campo cliente_cpf foi ajustado para refletir o relacionamento correto
INSERT INTO endereco (cliente_cpf, bairro, cidade, complemento, logradouro, tag)
VALUES ('00000000000', 'Bairro A', 'Cidade A', 'Complemento A', 'Logradouro A', 'Tag A'),
       ('00000000001', 'Bairro B', 'Cidade B', 'Complemento B', 'Logradouro B', 'Tag B');

-- Inserting data into the raca table
INSERT INTO raca (descricao)
VALUES ('Raca A'),
       ('Raca B');

-- Inserting data into the pets table
-- O campo cliente_cpf foi ajustado para refletir o relacionamento correto
INSERT INTO pets (data_nascimento, cliente_cpf, id_raca, nome)
VALUES ('2022-01-01', '00000000000', 1, 'Pet A'),
       ('2022-02-01', '00000000000', 2, 'Pet B'),
       ('2022-03-01', '00000000001', 1, 'Pet C');

-- Inserting data into the atendimento table
-- O campo cliente_cpf foi ajustado para refletir o relacionamento correto
INSERT INTO atendimento (data_atendimento, cliente_cpf, id_pet, valor)
VALUES ('2023-01-10 10:00:00', '00000000000', 1, 'Valor A'),
       ('2023-01-11 11:00:00', '00000000000', 2, 'Valor B'),
       ('2023-01-12 12:00:00', '00000000001', 3, 'Valor C');

