-- liquibase formatted sql

-- changeset antipin:1720502742229-1
CREATE SEQUENCE IF NOT EXISTS customer_seq START WITH 1 INCREMENT BY 1;

-- changeset antipin:1720502742229-2
CREATE SEQUENCE IF NOT EXISTS order_seq START WITH 1 INCREMENT BY 1;

-- changeset antipin:1720502742229-3
CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

-- changeset antipin:1720502742229-4
CREATE TABLE customers
(
    id             BIGINT       NOT NULL DEFAULT nextval('customer_seq'),
    firstname      VARCHAR(255) NOT NULL,
    lastname       VARCHAR(255) NOT NULL,
    email          VARCHAR(255),
    contact_number VARCHAR(255),
    CONSTRAINT pk_customers PRIMARY KEY (id)
);

-- changeset antipin:1720502742229-5
CREATE TABLE orders
(
    id               BIGINT       NOT NULL DEFAULT nextval('order_seq'),
    customer_id      BIGINT       NOT NULL,
    date             DATE,
    shipping_address VARCHAR(255) NOT NULL,
    total_price      NUMERIC      NOT NULL,
    status           VARCHAR(255) NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

-- changeset antipin:1720502742229-6
CREATE TABLE orders_products
(
    order_id   BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

-- changeset antipin:1720502742229-7
CREATE TABLE products
(
    id                BIGINT       NOT NULL DEFAULT nextval('product_seq'),
    name              VARCHAR(255) NOT NULL,
    description       VARCHAR(255),
    price             NUMERIC      NOT NULL,
    quantity_in_stock INTEGER      NOT NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

-- changeset antipin:1720502742229-8
ALTER TABLE orders
    ADD CONSTRAINT fk_orders_on_customer FOREIGN KEY (customer_id) REFERENCES customers (id);

-- changeset antipin:1720502742229-9
ALTER TABLE orders_products
    ADD CONSTRAINT fk_orders_products_on_order FOREIGN KEY (order_id) REFERENCES orders (id);

-- changeset antipin:1720502742229-10
ALTER TABLE orders_products
    ADD CONSTRAINT fk_orders_products_on_product FOREIGN KEY (product_id) REFERENCES products (id);

-- changeset antipin:1720502742229-11
INSERT INTO customers (id, firstname, lastname, email, contact_number)
VALUES (1, 'Firstname1', 'Lastname1', 'email1@example.com', '89000000000'),
       (2, 'Firstname2', 'Lastname2', 'email2@example.com', '89999999999');

-- changeset antipin:1720502742229-12
INSERT INTO products (id, name, description, price, quantity_in_stock)
VALUES (1, 'Product 1', 'Description for Product 1', 10.00, 100),
       (2, 'Product 2', 'Description for Product 2', 20.00, 200),
       (3, 'Product 3', 'Description for Product 3', 30.00, 300),
       (4, 'Product 4', 'Description for Product 4', 40.00, 400);

-- changeset antipin:1720502742229-13
INSERT INTO orders (id, customer_id, date, shipping_address, total_price, status)
VALUES (1, 1, '2024-07-01', '454000 Chelyabinsk', 40.00, 'ON_PROCESS'),
       (2, 2, '2024-07-02', 'Moscow, Red Square', 130.00, 'ON_DELIVERY');

-- changeset antipin:1720502742229-14
INSERT INTO orders_products (order_id, product_id)
VALUES (1, 1),
       (1, 1),
       (1, 2),
       (2, 3),
       (2, 3),
       (2, 3),
       (2, 4);