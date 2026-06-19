CREATE DATABASE IF NOT EXISTS zoologico_poo2;
USE zoologico_poo2;

CREATE TABLE IF NOT EXISTS especies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS animais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    peso DECIMAL(10,2) NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    especie_id BIGINT NOT NULL,
    caracteristica_especifica VARCHAR(100) NOT NULL,
    CONSTRAINT fk_animais_especies FOREIGN KEY (especie_id) REFERENCES especies(id)
);

CREATE TABLE IF NOT EXISTS alimentacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    animal_id BIGINT NOT NULL,
    alimento VARCHAR(100) NOT NULL,
    quantidade_kg DECIMAL(10,2) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    CONSTRAINT fk_alimentacoes_animais FOREIGN KEY (animal_id) REFERENCES animais(id)
);
