--liquibase formatted sql
--changeset dabrowskiw:1
CREATE TABLE COMPANY(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO COMPANY (name) VALUES ('Apple');
INSERT INTO COMPANY (name) VALUES ('Microsoft');
INSERT INTO COMPANY (name) VALUES ('Tesla');