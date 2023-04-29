--liquibase formatted sql
--changeset dabrowskiw:7
CREATE TABLE user_role_2_company(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role VARCHAR(128) NOT NULL,
    company_id BIGINT NOT NULL
);
alter table user_role_2_company add constraint fk_users_user_role_2_company foreign key (user_id) references users(id);
alter table user_role_2_company add constraint fk_company_user_role_2_company_id foreign key (company_id) references company(id);