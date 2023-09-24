--liquibase formatted sql
--changeset dabrowskiw:14
ALTER TABLE branch
ADD voivodeship VARCHAR(64) AFTER slug;