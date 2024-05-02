--liquibase formatted sql
--changeset dabrowskiw:15
CREATE TABLE html_page(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    content TEXT,
    slug VARCHAR(128) NOT NULL UNIQUE
);