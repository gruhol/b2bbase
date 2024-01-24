--liquibase formatted sql
--changeset dabrowskiw:9
ALTER TABLE branch
ADD voivodeship VARCHAR(64) AFTER slug;