--liquibase formatted sql
--changeset dabrowskiw:10
CREATE TABLE category_2_company (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL
);
