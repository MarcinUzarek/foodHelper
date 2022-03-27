--liquibase formatted sql
--changeset marcinUzarek:3

CREATE TABLE intolerances (
  id bigint NOT NULL AUTO_INCREMENT,
  product varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;