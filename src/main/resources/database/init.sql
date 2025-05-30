create table if not exists authors
(
    id   serial
        primary key,
    name varchar(255) not null
);

alter table authors
    owner to postgres;

create table if not exists genres
(
    id   serial
        primary key,
    name varchar(255) not null
);

alter table genres
    owner to postgres;

create table if not exists books
(
    id          serial
        primary key,
    title       varchar(255) not null,
    pageamount  integer      not null,
    authorid    integer      not null
        constraint fk_author
            references authors
            on delete cascade,
    genreid     integer      not null
        constraint fk_genre
            references genres
            on delete cascade,
    year        integer,
    description text
);

alter table books
    owner to postgres;


