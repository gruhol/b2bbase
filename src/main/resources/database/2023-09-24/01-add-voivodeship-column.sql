--liquibase formatted sql
--changeset dabrowskiw:8
ALTER TABLE branch
ADD voivodeship VARCHAR(64) AFTER slug;