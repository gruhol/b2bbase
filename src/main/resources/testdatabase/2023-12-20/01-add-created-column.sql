--liquibase formatted sql
--changeset dabrowskiw:11
ALTER TABLE company
ADD created datetime AFTER logo;
