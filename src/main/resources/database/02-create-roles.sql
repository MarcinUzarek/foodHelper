--liquibase formatted sql
--changeset marcinUzarek:2

CREATE TABLE roles (
  id bigint NOT NULL AUTO_INCREMENT,
  name varchar(20) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;