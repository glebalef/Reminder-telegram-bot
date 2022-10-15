-- liquibase formatted sql

- changeset gdvoynykh:1
CREATE TABLE notificationTask (
    ID BIGINT,
    CHATID BIGINT,
    TEXT VARCHAR,
    DATE DATE,
    TIME TIME,
    PRIMARY KEY (ID)
    );