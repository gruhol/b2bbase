--liquibase formatted sql
--changeset dabrowskiw:16
CREATE TABLE blog_category(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    slug VARCHAR(256) NOT NULL,
    description TEXT
);