--liquibase formatted sql

--changeset rniyazov:1
CREATE TABLE IF NOT EXISTS product
(
    id    SERIAL PRIMARY KEY,
    qua   INT                NOT NULL,
    name  VARCHAR(32) UNIQUE NOT NULL,
    cost  DECIMAL            NOT NULL,
    promo VARCHAR(3)         NOT NULL
);

--changeset rniyazov:2
CREATE TABLE IF NOT EXISTS card
(
    id    SERIAL PRIMARY KEY,
    bonus VARCHAR(32) NOT NULL
);

--changeset rniyazov:3
INSERT INTO product (qua, name, cost, promo)
VALUES (3, 'Apple', 1.65, 'NO'),
       (1, 'Milk', 1.15, 'NO'),
       (1, 'Meat', 2.45, 'NO'),
       (10, 'Eggs', 1.35, 'YES'),
       (2, 'Cheese', 1.95, 'YES'),
       (1, 'Bread', 1.00, 'YES'),
       (1, 'Fish', 2.05, 'YES'),
       (1, 'Oil', 1.05, 'YES'),
       (3, 'Chocolate', 1.10, 'YES');

--changeset rniyazov:4
INSERT INTO card (bonus)
VALUES ('GoldCard'),
       ('SilverCard'),
       ('StandardCard'),
       ('Not');