--liquibase formatted sql
--changeset dabrowskiw:21
ALTER TABLE category
ADD short_description TEXT AFTER description;