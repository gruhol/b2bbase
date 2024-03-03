--liquibase formatted sql
--changeset dabrowskiw:14
ALTER TABLE users
ADD regulations_agreement BOOLEAN default false AFTER enabled;
ALTER TABLE users
ADD email_agreement BOOLEAN default false AFTER regulations_agreement;
ALTER TABLE users
ADD sms_agreement BOOLEAN default false AFTER email_agreement;