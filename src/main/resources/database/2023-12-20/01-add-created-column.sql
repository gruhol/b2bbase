--liquibase formatted sql
--changeset dabrowskiw:10
ALTER TABLE company
ADD created datetime AFTER logo;
