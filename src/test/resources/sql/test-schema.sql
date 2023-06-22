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
    id                bigserial not null
        constraint base_fee_rules_pkey
            primary key,
    city              varchar(255),
    regional_base_fee float8,
    vehicle_type      varchar(255),
    wmo_code          int8
);

create table if not exists delivery_fee
(
    id             bigserial not null
        constraint delivery_fee_pkey
            primary key,
    city           varchar(255),
    delivery_fee   float8,
    rest_timestamp timestamp(6),
    timestamp      timestamptz(6),
    vehicle_type   varchar(255),
    weather_id     int8
);

create table if not exists extra_fee_rules_temperature
(
    id                      bigserial not null
        constraint extra_fee_rules_temperature_pkey
            primary key,
    end_temperature_range   float8,
    temperature_fee         float8,
    start_temperature_range float8
);

create table if not exists extra_fee_rules_weather_phenomenon
(
    id                      bigserial not null
        constraint extra_fee_rules_weather_phenomenon_pkey
            primary key,
    weather_phenomenon_fee  float8,
    weather_phenomenon_name varchar(255)
);

create table if not exists extra_fee_rules_windspeed
(
    id                    bigserial not null
        constraint extra_fee_rules_windspeed_pkey
            primary key,
    end_windspeed_range   float8,
    windspeed_fee         float8,
    start_windspeed_range float8
);

create table if not exists weather_data
(
    id                 bigserial not null
        constraint weather_data_pkey
            primary key,
    air_temperature    float8,
    rest_timestamp     timestamp(6),
    station_name       varchar(255),
    timestamp          timestamptz(6),
    weather_phenomenon varchar(255),
    wind_speed         float8,
    wmo_code           int8
);

create table if not exists users
(
    id         int8 default nextval('users_id_seq'::regclass) not null
        constraint users_pkey
            primary key,
    created_at timestamptz(6),
    email      varchar(255)                                   not null
        constraint uk_6dotkott2kjsp8vw4d0m25fb7
            unique,
    firstname  varchar(255)                                   not null,
    lastname   varchar(255)                                   not null,
    password   varchar(255)                                   not null,
    role       varchar(255),
    telephone  varchar(255)                                   not null,
    updated_at timestamptz(6),
    username   varchar(255)                                   not null
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

create table if not exists tokens
(
    id         int8 not null
        constraint tokens_pkey
            primary key,
    expired    bool not null,
    revoked    bool not null,
    token      varchar(255)
        constraint uk_na3v9f8s7ucnj16tylrs822qj
            unique,
    token_type varchar(255),
    user_id    int8
        constraint "FKck21ekaqng736h8ohx6l9xrgv"
            references users
);

create table if not exists users_admins
(
    id      int8 default nextval('users_admins_id_seq'::regclass) not null
        constraint users_admins_pkey
            primary key,
    level   int8,
    user_id int8
        constraint "FK8twaejk4y26raenptwobhr3hh"
            references users
);

create table if not exists users_owners
(
    id       int8 default nextval('users_owners_id_seq'::regclass) not null
        constraint users_owners_pkey
            primary key,
    approved bool,
    user_id  int8
        constraint "FKo77yq12byfu5mtmk55iicifd1"
            references users
);

create table if not exists restaurants
(
    id     int8 default nextval('restaurants_id_seq'::regclass) not null
        constraint restaurants_pkey
            primary key,
    "desc" varchar(255),
    image  varchar(255),
    name   varchar(255),
    phone  varchar(255),
    theme  varchar(255),
    owner  int8
        constraint "FK4srosi24quu80tqaxu8u5hy68"
            references users_owners
);

create table if not exists addresses
(
    id            int8 default nextval('addresses_id_seq'::regclass) not null
        constraint addresses_pkey
            primary key,
    apt_number    varchar(255),
    city          varchar(255),
    country       varchar(255),
    county        varchar(255),
    house_number  varchar(255),
    street        varchar(255),
    zip_code      varchar(255),
    customer_id   int8,
    restaurant_id int8
        constraint "FKpf4f13jpma2iawp0qptbfyr3p"
            references restaurants
);

create table if not exists menus
(
    id            int8 default nextval('menus_id_seq'::regclass) not null
        constraint menus_pkey
            primary key,
    hidden        varchar(255),
    name          varchar(255),
    owner         int8
        constraint "FKammekxrkuurefsa9u3p2smkww"
            references users_owners,
    restaurant_id int8
        constraint "FK30b21u83g0vg4w6lbmim3u050"
            references restaurants
);

create table if not exists items
(
    id            int8 default nextval('items_id_seq'::regclass) not null
        constraint items_pkey
            primary key,
    allergens     varchar(255),
    "desc"        varchar(255),
    image         varchar(255),
    ingredients   varchar(255),
    name          varchar(255),
    price         float8,
    menu_id       int8
        constraint "FKrcf6m1wsopmdjnivrldc6rf7r"
            references menus,
    restaurant_id int8
        constraint "FKlaf24ka527sfksj9t7n4vivem"
            references restaurants
);

create table if not exists users_customers
(
    id         int8 default nextval('users_customers_id_seq'::regclass) not null
        constraint users_customers_pkey
            primary key,
    address_id int8
        constraint "FKl47ukmldgc2yw7lgv48jtea5b"
            references addresses,
    user_id    int8
        constraint uk_jq4a9g50e41d039r7f66tkpx
            unique
        constraint "FK2lcvw0g0312l9bgvy2ovdklgk"
            references users
);

create table if not exists orders
(
    id            int8 default nextval('orders_id_seq'::regclass) not null
        constraint orders_pkey
            primary key,
    delivery_fee  float8,
    item_price    float8,
    datetime      timestamptz(6),
    status        varchar(255),
    total_price   float8,
    customer_id   int8
        constraint "FK658utqfe4f1pk5ovqr5r6d94w"
            references users_customers,
    restaurant_id int8
        constraint "FKia5lqhdlvp38fxhrbb8xlr719"
            references restaurants
);

create table if not exists order_items
(
    order_id int8 not null
        constraint "FK6gg8woymk43fp55jtjlgkd1eh"
            references orders,
    item_id  int8 not null
        constraint "FKo9ifqtev705wjypu43gf1q7w7"
            references items
);