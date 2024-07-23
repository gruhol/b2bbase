--liquibase formatted sql
--changeset dabrowskiw:19
CREATE TABLE price_list(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(128) NOT NULL,
    promotion_price BOOLEAN NOT NULL,
    price int NOT NULL,
    start_date Date NOT NULL,
    end_date Date NOT NULL,
    is_active BOOLEAN default true
);