create schema if not exists public;

create sequence if not exists base_fee_rules_id_seq;
create sequence if not exists delivery_fee_id_seq;
create sequence if not exists extra_fee_rules_temperature_id_seq;
create sequence if not exists extra_fee_rules_weather_phenomenon_id_seq;
create sequence if not exists extra_fee_rules_windspeed_id_seq;
create sequence if not exists weather_data_id_seq;
create sequence if not exists addresses_id_seq;
create sequence if not exists items_id_seq;
create sequence if not exists menus_id_seq;
create sequence if not exists orders_id_seq;
create sequence if not exists restaurants_id_seq;
create sequence if not exists tokens_id_seq;
create sequence if not exists users_admins_id_seq;
create sequence if not exists users_customers_id_seq;
create sequence if not exists users_owners_id_seq;
create sequence if not exists users_id_seq;

create table if not exists base_fee_rules
(
    id                bigserial
        primary key,
    city              varchar(255),
    regional_base_fee double precision,
    vehicle_type      varchar(255),
    wmo_code          bigint
);

create table if not exists delivery_fee
(
    id             bigserial
        primary key,
    city           varchar(255),
    delivery_fee   double precision,
    rest_timestamp timestamp(6),
    timestamp      timestamp(6) with time zone,
    vehicle_type   varchar(255),
    weather_id     bigint
);

create table if not exists extra_fee_rules_temperature
(
    id                      bigserial
        primary key,
    end_temperature_range   double precision,
    temperature_fee         double precision,
    start_temperature_range double precision
);

create table if not exists extra_fee_rules_weather_phenomenon
(
    id                      bigserial
        primary key,
    weather_phenomenon_fee  double precision,
    weather_phenomenon_name varchar(255)
);

create table if not exists extra_fee_rules_windspeed
(
    id                    bigserial
        primary key,
    end_windspeed_range   double precision,
    windspeed_fee         double precision,
    start_windspeed_range double precision
);

create table if not exists weather_data
(
    id                 bigserial
        primary key,
    air_temperature    double precision,
    rest_timestamp     timestamp(6),
    station_name       varchar(255),
    timestamp          timestamp(6) with time zone,
    weather_phenomenon varchar(255),
    wind_speed         double precision,
    wmo_code           bigint
);

create table if not exists users
(
    id         bigserial
        primary key,
    created_at timestamp(6) with time zone,
    email      varchar(255) not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    firstname  varchar(255) not null,
    lastname   varchar(255) not null,
    password   varchar(255) not null,
    role       varchar(255),
    telephone  varchar(255) not null,
    updated_at timestamp(6) with time zone,
    username   varchar(255) not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

create table if not exists tokens
(
    id         bigint  not null
        primary key,
    expired    boolean not null,
    revoked    boolean not null,
    token      varchar(255)
        constraint uk_na3v9f8s7ucnj16tylrs822qj
            unique,
    token_type varchar(255),
    user_id    bigint
        constraint "FKck21ekaqng736h8ohx6l9xrgv"
            references users
);

create table if not exists users_admins
(
    user_id bigint not null
        primary key
        constraint "FK8twaejk4y26raenptwobhr3hh"
            references users,
    level   bigint
);

create table if not exists users_owners
(
    user_id  bigint not null
        primary key
        constraint "FKo77yq12byfu5mtmk55iicifd1"
            references users,
    approved boolean
);

create table if not exists restaurants
(
    id          bigserial
        primary key,
    description varchar(255),
    image       varchar(255),
    name        varchar(255),
    phone       varchar(255),
    theme       varchar(255),
    owner_id    bigint
        constraint "FK85k6q9305o08c5mw153y9gu2a"
            references users_owners
);

create table if not exists addresses
(
    id            bigserial
        primary key,
    apt_number    varchar(255),
    city          varchar(255),
    country       varchar(255),
    county        varchar(255),
    house_number  varchar(255),
    street        varchar(255),
    zip_code      integer,
    customer_id   bigint
        constraint "FK3t97fttinei9cop4hoapqh2cw"
            references users_customers,
    restaurant_id bigint
        constraint uk_dvfu820vikpy2nymtmu6c2wy2
            unique
        constraint "FKpf4f13jpma2iawp0qptbfyr3p"
            references restaurants
);

create table if not exists menus
(
    id            bigserial
        primary key,
    name          varchar(255),
    visibility    boolean,
    owner_id      bigint
        constraint "FKi3q9ifvn71wpp42uklpxntqt2"
            references users_owners,
    restaurant_id bigint
        constraint "FK30b21u83g0vg4w6lbmim3u050"
            references restaurants
);

create table if not exists items
(
    id            bigserial
        primary key,
    allergens     varchar(255),
    description   varchar(255),
    image         varchar(255),
    ingredients   varchar(255),
    name          varchar(255),
    price         double precision,
    menu_id       bigint
        constraint "FKrcf6m1wsopmdjnivrldc6rf7r"
            references menus,
    owner_id      bigint
        constraint "FKbibljgxxyuebr3l9ib2j7vsnp"
            references users_owners,
    restaurant_id bigint
        constraint "FKlaf24ka527sfksj9t7n4vivem"
            references restaurants
);

create table if not exists users_customers
(
    user_id    bigint not null
        primary key
        constraint "FK2lcvw0g0312l9bgvy2ovdklgk"
            references users,
    address_id bigint
        constraint "FKl47ukmldgc2yw7lgv48jtea5b"
            references addresses
);

create table if not exists orders
(
    id            bigserial
        primary key,
    delivery_fee  double precision,
    item_price    double precision,
    datetime      timestamp(6) with time zone,
    status        varchar(255),
    total_price   double precision,
    customer_id   bigint
        constraint "FK658utqfe4f1pk5ovqr5r6d94w"
            references users_customers,
    restaurant_id bigint
        constraint "FKia5lqhdlvp38fxhrbb8xlr719"
            references restaurants
);

create table if not exists order_items
(
    item_id  bigint not null
        constraint "FKo9ifqtev705wjypu43gf1q7w7"
            references items,
    order_id bigint not null
        constraint "FK6gg8woymk43fp55jtjlgkd1eh"
            references orders,
    quantity integer,
    primary key (item_id, order_id)
);
