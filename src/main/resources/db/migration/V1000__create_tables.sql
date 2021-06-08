CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       login VARCHAR(64) NOT NULL,
                       email VARCHAR(64) NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       activate VARCHAR(4) NOT NULL,
                       enabled boolean NOT NULL default true,
                       role VARCHAR(32) NOT NULL,
                       PRIMARY KEY (id)
);
CREATE TABLE todos (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       date DATETIME NOT NULL,
                       name VARCHAR(64) NOT NULL,
                       description VARCHAR(256) NOT NULL,
                       status VARCHAR(16) NOT NULL,
                       user_id BIGINT NOT NULL,
                       PRIMARY KEY (id)
);
