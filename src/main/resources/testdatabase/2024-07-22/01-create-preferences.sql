--liquibase formatted sql
--changeset dabrowskiw:18
CREATE TABLE preferences(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    preference_key VARCHAR(128) NOT NULL,
    preference_value VARCHAR(128) NOT NULL
);