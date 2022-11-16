drop table if exists CATEGORY_ITEM;

drop table if exists CATEGORY;

drop table if exists DELIVERY;

drop table if exists ITEM;

drop table if exists MEMBER;

drop table if exists ORDERS;

drop table if exists ORDER_ITEM;


create table CATEGORY
(
    CATEGORY_ID BIGINT not null
        primary key,
    NAME        CHARACTER VARYING(255),
    PARENT_ID   BIGINT,
    constraint FK2Y94SVPMQTTX80MSHYNY85WQR
        foreign key (PARENT_ID) references CATEGORY
);

create table DELIVERY
(
    DELIVERY_ID BIGINT not null
        primary key,
    CITY        CHARACTER VARYING(255),
    STREET      CHARACTER VARYING(255),
    ZIPCODE     CHARACTER VARYING(255),
    STATUS      CHARACTER VARYING(255)
);

create table ITEM
(
    DTYPE          CHARACTER VARYING(31) not null,
    ITEM_ID        BIGINT                not null
        primary key,
    NAME           CHARACTER VARYING(255),
    PRICE          INTEGER               not null,
    STOCK_QUANTITY INTEGER               not null,
    ARTIST         CHARACTER VARYING(255),
    ETC            CHARACTER VARYING(255),
    AUTHOR         CHARACTER VARYING(255),
    ISBN           CHARACTER VARYING(255),
    ACTOR          CHARACTER VARYING(255),
    DIRECTOR       CHARACTER VARYING(255)
);

create table CATEGORY_ITEM
(
    CATEGORY_ID BIGINT not null,
    ITEM_ID     BIGINT not null,
    constraint FKCQ2N0OPF5SHYH84EX1FHUKCBH
        foreign key (CATEGORY_ID) references CATEGORY,
    constraint FKU8B4LWQUTCDQ3363GF6MLUJQ
        foreign key (ITEM_ID) references ITEM
);

create table MEMBER
(
    MEMBER_ID BIGINT not null
        primary key,
    CITY      CHARACTER VARYING(255),
    STREET    CHARACTER VARYING(255),
    ZIPCODE   CHARACTER VARYING(255),
    NAME      CHARACTER VARYING(255)
        constraint UK9ESVGIKRMTI1V7CI6V453IMDC
            unique
);

create table ORDERS
(
    ORDER_ID    BIGINT not null
        primary key,
    ORDER_DATE  TIMESTAMP,
    STATUS      CHARACTER VARYING(255),
    DELIVERY_ID BIGINT,
    MEMBER_ID   BIGINT
);

create table ORDER_ITEM
(
    ORDER_ITEM_ID BIGINT  not null
        primary key,
    COUNT         INTEGER not null,
    ORDER_PRICE   INTEGER not null,
    ITEM_ID       BIGINT  not null,
    ORDER_ID      BIGINT  not null
);

