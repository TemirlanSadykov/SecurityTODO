CREATE TABLE users (
                       id serial  NOT NULL,
                       login VARCHAR(64) NOT NULL,
                       email VARCHAR(64) NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       activate BOOLEAN NOT NULL,
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       role_id BIGINT NOT NULL,
                       PRIMARY KEY (id)
);
CREATE TABLE todos (
                       id serial NOT NULL,
                       date DATE NOT NULL,
                       name VARCHAR(64) NOT NULL,
                       description VARCHAR(256) NOT NULL,
                       status VARCHAR(32),
                       user_id BIGINT NOT NULL,
                       term TIMESTAMP,
                       PRIMARY KEY (id)
);
CREATE TABLE roles (
                       id serial NOT NULL,
                       name VARCHAR(64) NOT NULL,
                       link VARCHAR(64) NOT NULL,
                       PRIMARY KEY (id)
);
