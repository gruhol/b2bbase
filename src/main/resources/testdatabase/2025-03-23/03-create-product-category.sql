--liquibase formatted sql
--changeset dabrowskiw:26
CREATE TABLE product_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    parent_id BIGINT,
    slug VARCHAR(256) NOT NULL,
    description TEXT
);