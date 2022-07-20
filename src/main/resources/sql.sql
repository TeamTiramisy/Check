CREATE TABLE product
(
    id    SERIAL PRIMARY KEY,
    qua   INT                NOT NULL,
    name  VARCHAR(32) UNIQUE NOT NULL,
    cost  DECIMAL            NOT NULL,
    promo VARCHAR(3)         NOT NULL
);

CREATE TABLE card
(
    id    SERIAL PRIMARY KEY,
    bonus VARCHAR(32) NOT NULL
);

INSERT INTO product (qua, name, cost, promo)
VALUES (3, 'Яблоко', 1.65, 'NO'),
       (1, 'Молоко', 1.15, 'NO'),
       (1, 'Мясо', 2.45, 'NO'),
       (10, 'Яйца', 1.35, 'YES'),
       (2, 'Сыр', 1.95, 'YES'),
       (1, 'Хлеб', 1.00, 'YES'),
       (1, 'Рыба', 2.05, 'YES'),
       (1, 'Масло', 1.05, 'YES'),
       (3, 'Шоколад', 1.10, 'YES');

INSERT INTO card (bonus)
VALUES ('GoldCard');