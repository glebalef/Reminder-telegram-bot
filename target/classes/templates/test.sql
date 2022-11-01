CREATE TABLE requests (
    id BIGINT,
    chat_id BIGINT,
    message VARCHAR,
    datetime VARCHAR,
    PRIMARY KEY (id)
);

SELECT * FROM requests;
