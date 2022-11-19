-- liquibase formatted sql

- changeset gleb:1
CREATE TABLE notification_task (
                          id BIGINT,
                          chat_id BIGSERIAL,
                          message VARCHAR,
                          datetime TIMESTAMP,
                          PRIMARY KEY (id)
);
