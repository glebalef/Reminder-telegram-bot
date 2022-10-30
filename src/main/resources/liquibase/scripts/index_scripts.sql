--liquibase formatted sql

- changeset gleb:1

CREATE TABLE notification_task (
                          id BIGINT,
                          chat_id BIGINT,
                          message VARCHAR,
                          datetime VARCHAR,
                          PRIMARY KEY (id)
);

- changeset gleb:2
ALTER TABLE notification_task DROP COLUMN datetime;
ALTER TABLE notification_task ADD COLUMN date DATE, ADD COLUMN time TIME;

- changeset gleb:3
ALTER TABLE notification_task DROP COLUMN date, DROP COLUMN time, ADD COLUMN datetime varchar;

- changeset gleb:4
ALTER TABLE notification_task DROP COLUMN datetime;

- changeset gleb:5
ALTER TABLE notification_task
ALTER COLUMN id SET NOT NULL;