--liquibase formatted sql
--changeset dabrowskiw:6
CREATE TABLE social (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(64) NOT NULL,
    url VARCHAR(128) NOT NULL,
    company_id BIGINT NOT NULL
);
alter table social add constraint fk_company_social_id foreign key (company_id) references company(id);