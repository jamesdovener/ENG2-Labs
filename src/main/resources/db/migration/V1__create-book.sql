create sequence hibernate_sequence;

create table bookDTO (
    id bigint primary key not null,
    title varchar(255) not null,
    authorDTO varchar(255) not null
);