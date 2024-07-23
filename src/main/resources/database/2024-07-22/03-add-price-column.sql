--liquibase formatted sql
--changeset dabrowskiw:18
ALTER TABLE subscription_order
ADD price int NOT NULL AFTER subscription_type;