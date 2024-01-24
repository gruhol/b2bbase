--liquibase formatted sql
--changeset dabrowskiw:3
CREATE TABLE branch(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    headquarter BOOLEAN default false,
    slug VARCHAR(256) NOT NULL,
    post_code VARCHAR(6) NOT NULL,
    city VARCHAR(32) NOT NULL,
    street VARCHAR(128) NOT NULL,
    house_number VARCHAR(16) NOT NULL,
    office_number VARCHAR(16),
    email VARCHAR(256),
    phone VARCHAR(11),
    latitude VARCHAR(128),
    longitude VARCHAR(128),
    company_id BIGINT NOT NULL
);
--liquibase formatted sql
--changeset dabrowskiw:4
alter table branch add constraint fk_company_branch_id foreign key (company_id) references company(id);