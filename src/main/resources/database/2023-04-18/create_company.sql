--liquibase formatted sql
--changeset dabrowskiw:1
CREATE TABLE COMPANY(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);