--liquibase formatted sql
--changeset dabrowskiw:24
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ean VARCHAR(13) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    default_image_url VARCHAR(255)
);