drop table if exists shop_product;
drop table if exists shops;
drop table if exists products;

create table if not exists shops
(
    id         bigint auto_increment
        primary key,
    name varchar(255) not null,
    address  varchar(255) null,
    status  varchar(255) not null
);

create table if not exists products
(
    id               bigint auto_increment
        primary key,
    name        varchar(255) not null,
    category        varchar(255) not null,
    weight        float          null,
    description      text         null,
    price         float          null
);

create table if not exists shop_product
(
    shop_id bigint not null,
    product_id   bigint not null,
    primary key (shop_id, product_id),
    foreign key (shop_id) references shops (id) on delete cascade,
    foreign key (product_id) references products (id) on delete cascade
);
