--liquibase formatted sql
--changeset dabrowskiw:8
CREATE TABLE verification_link (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    id_user BIGINT NOT NULL,
    token VARCHAR(50),
    is_consumed boolean not null,
    expired_date DATE not null,
    issued_date DATE not null,
    confirmed_date DATE
);