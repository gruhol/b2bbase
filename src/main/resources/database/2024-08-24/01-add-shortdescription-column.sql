--liquibase formatted sql
--changeset dabrowskiw:19
ALTER TABLE category
ADD short_description TEXT AFTER description;