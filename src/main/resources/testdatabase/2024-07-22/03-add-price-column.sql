--liquibase formatted sql
--changeset dabrowskiw:20
ALTER TABLE subscription_order
ADD price int NOT NULL AFTER subscription_type;