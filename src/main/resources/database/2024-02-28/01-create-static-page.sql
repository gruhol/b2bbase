--liquibase formatted sql
--changeset dabrowskiw:12
CREATE TABLE static_page(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    added_date DATETIME NOT NULL,
    edit_date DATETIME NOT NULL,
    content TEXT,
    slug VARCHAR(128) NOT NULL UNIQUE
);