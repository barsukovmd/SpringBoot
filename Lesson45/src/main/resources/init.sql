--------------------------------------------------------
--  DDL for schema ESHOP
--------------------------------------------------------
DROP SCHEMA IF EXISTS ESHOP;
CREATE SCHEMA IF NOT EXISTS ESHOP;

--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------
DROP TABLE IF EXISTS ESHOP.CATEGORIES;
CREATE TABLE IF NOT EXISTS ESHOP.CATEGORIES
(
    ID         INT          NOT NULL AUTO_INCREMENT,
    NAME       VARCHAR(45)  NOT NULL,
    IMAGE_PATH VARCHAR(100) NOT NULL,
    RATING     INT          NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE INDEX IDX_CATEGORIES_CATEGORY_ID_UNIQUE (ID ASC),
    UNIQUE INDEX IDX_CATEGORIES_NAME_UNIQUE (NAME ASC)
);

--------------------------------------------------------
--  DDL for Table USERS
--------------------------------------------------------
DROP TABLE IF EXISTS ESHOP.USERS;
CREATE TABLE IF NOT EXISTS ESHOP.USERS
(
    ID       INT          NOT NULL AUTO_INCREMENT,
    NAME     VARCHAR(45)  NOT NULL,
    SURNAME  VARCHAR(45)  NOT NULL,
    EMAIL    VARCHAR(200) NOT NULL,
    PASSWORD VARCHAR(50)  NOT NULL,
    BIRTHDAY DATETIME     NOT NULL,
    BALANCE  INT          NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE INDEX IDX_USERS_USER_ID_UNIQUE (ID ASC),
    UNIQUE INDEX IDX_USERS_EMAIL_UNIQUE (EMAIL ASC),
    UNIQUE INDEX IDX_USERS_PASSWORD_UNIQUE (PASSWORD ASC)
);

--------------------------------------------------------
--  DDL for Table ORDERS
--------------------------------------------------------
DROP TABLE IF EXISTS ESHOP.ORDERS;
CREATE TABLE IF NOT EXISTS ESHOP.ORDERS
(
    ID      INT      NOT NULL AUTO_INCREMENT,
    DATE    DATETIME NOT NULL,
    PRICE   INT      NOT NULL,
    USER_ID INT      NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE INDEX IDX_ORDERS_ID_UNIQUE (ID ASC),
    CONSTRAINT FK_ORDERS_USER_ID_USERS_ID
        FOREIGN KEY (USER_ID)
            REFERENCES ESHOP.USERS (ID)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

--------------------------------------------------------
--  DDL for Table PRODUCTS
--------------------------------------------------------
DROP TABLE IF EXISTS ESHOP.PRODUCTS;
CREATE TABLE IF NOT EXISTS ESHOP.PRODUCTS
(
    ID          INT          NOT NULL AUTO_INCREMENT,
    NAME        VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(300) NULL,
    PRICE       INT          NOT NULL,
    IMAGE_PATH  VARCHAR(300) NULL,
    CATEGORY_ID INT          NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE INDEX IDX_PRODUCTS_ID_UNIQUE (ID ASC),
    CONSTRAINT FK_PRODUCTS_CATEGORY_ID_CATEGORIES_ID
        FOREIGN KEY (CATEGORY_ID)
            REFERENCES ESHOP.CATEGORIES (ID)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

--------------------------------------------------------
--  DDL for Table ORDERS_PRODUCTS
--------------------------------------------------------
DROP TABLE IF EXISTS ESHOP.ORDERS_PRODUCTS;
CREATE TABLE IF NOT EXISTS ESHOP.ORDERS_PRODUCTS
(
    ORDER_ID   INT NOT NULL,
    PRODUCT_ID INT NOT NULL,
    PRIMARY KEY (ORDER_ID, PRODUCT_ID),
    CONSTRAINT FK_ORDERS_PRODUCTS_ORDER_ID_ORDERS_ID
        FOREIGN KEY (ORDER_ID)
            REFERENCES ORDERS (ID),
    CONSTRAINT FK_ORDERS_PRODUCTS_PRODUCT_ID_PRODUCTS_ID
        FOREIGN KEY (PRODUCT_ID)
            REFERENCES PRODUCTS (ID)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);