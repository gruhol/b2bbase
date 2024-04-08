--liquibase formatted sql
--changeset dabrowskiw:14
CREATE TABLE blog(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL,
    add_date DATETIME NOT NULL,
    edit_date DATETIME NOT NULL,
    content TEXT,
    author BIGINT  NOT NULL,
    slug VARCHAR(128) NOT NULL UNIQUE
);