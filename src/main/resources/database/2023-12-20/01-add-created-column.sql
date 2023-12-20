--liquibase formatted sql
--changeset dabrowskiw:16
ALTER TABLE company
ADD created datetime AFTER logo;
