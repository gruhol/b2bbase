--liquibase formatted sql
--changeset dabrowskiw:4
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(128),
    username VARCHAR(256) NOT NULL UNIQUE,
    phone VARCHAR(11) NOT NULL,
    password varchar(500) not null,
    enabled boolean not null
);
--changeset dabrowskiw:6
create table authorities (
    username varchar(256) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
--changeset dabrowskiw:8
create unique index ix_auth_username on authorities (username,authority);
--changeset dabrowskiw:9
insert into users (username, phone, password, enabled)
values ('admin', '600000000', '{bcrypt}$2a$10$upzXFsFUOClFRR69OMKF8eajGMRs0vhcSHqvNDKy9yfW45w7o9z6O', true);
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');