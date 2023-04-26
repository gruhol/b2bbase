--liquibase formatted sql
--changeset dabrowskiw:5
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    surename VARCHAR(128) NOT NULL,
    email VARCHAR(256) NOT NULL,
    phone VARCHAR(11) NOT NULL
);