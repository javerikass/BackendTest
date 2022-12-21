CREATE SCHEMA item_storage;

SET search_path = item_storage;

CREATE TABLE product
(
    id              serial primary key,
    name            varchar(255)          not null,
    price           double precision      not null,
    discount        float   default 0,
    promotionalItem boolean default false not null
);

CREATE TABLE discount_card
(
    id       serial,
    item_Id  varchar(255),
    name     varchar(255) not null ,
    discount double precision not null,
    PRIMARY KEY (id, item_Id)
);
