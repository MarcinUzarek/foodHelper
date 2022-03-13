--liquibase formatted sql
--changeset marcinUzarek:7

INSERT INTO roles VALUES
(1, 'USER'),
(2, 'ADMIN'),
(3, 'MODERATOR');