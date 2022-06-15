--liquibase formatted sql
--changeset marcinUzarek:1

CREATE DATABASE IF NOT EXISTS `food_helper`;

CREATE TABLE users (
  id bigint NOT NULL AUTO_INCREMENT,
  creation_time date DEFAULT NULL,
  email varchar(60) NOT NULL,
  is_enabled bit(1) DEFAULT NULL,
  name varchar(30) NOT NULL,
  password varchar(80) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_users_email (email))
   ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;