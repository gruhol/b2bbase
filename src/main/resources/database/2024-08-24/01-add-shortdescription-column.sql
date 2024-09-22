--liquibase formatted sql
--changeset dabrowskiw:19
ALTER TABLE category
ADD short_description TEXT AFTER description;
ALTER TABLE category
ADD h1 VARCHAR(256) AFTER short_description;
ALTER TABLE category
ADD title VARCHAR(128) AFTER h1;