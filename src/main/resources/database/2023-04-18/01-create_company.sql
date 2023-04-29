--liquibase formatted sql
--changeset dabrowskiw:1
CREATE TABLE company(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(300) NOT NULL,
    slug VARCHAR(300) NOT NULL,
    type VARCHAR(32) NOT NULL,
    legal_form VARCHAR(50) NOT NULL,
    nip VARCHAR(11) NOT NULL,
    regon VARCHAR(14) NOT NULL,
    krs VARCHAR(10),
    email VARCHAR(256) NOT NULL,
    phone VARCHAR(11) NOT NULL,
    www_site VARCHAR(256) NOT NULL,
    www_store VARCHAR(256) NOT NULL,
    edi_cooperation BOOLEAN default false,
    api_cooperation BOOLEAN default false,
    product_file_cooperation BOOLEAN default false,
    description TEXT,
    logo VARCHAR(256)
);