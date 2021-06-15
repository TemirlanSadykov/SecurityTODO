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
CREATE TYPE stat as ENUM('NEW', 'IN_PROCESS', 'DONE', 'WELL_DONE', 'SUPPER_DONE', 'AWFUL', 'SUCCESSFUL', 'BEAUTIFUL');
CREATE TABLE todos (
                       id serial NOT NULL,
                       date TIMESTAMP NOT NULL,
                       name VARCHAR(64) NOT NULL,
                       description VARCHAR(256) NOT NULL,
                       status stat,
                       user_id BIGINT NOT NULL,
                       PRIMARY KEY (id)
);
CREATE TABLE roles (
                       id serial NOT NULL,
                       name VARCHAR(64) NOT NULL,
                       PRIMARY KEY (id)
);
