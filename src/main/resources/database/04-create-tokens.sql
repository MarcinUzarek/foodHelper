--liquibase formatted sql
--changeset marcinUzarek:4

CREATE TABLE tokens (
  id bigint NOT NULL AUTO_INCREMENT,
  value varchar(255) DEFAULT NULL,
  user_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_tokens_userId (user_id),
  CONSTRAINT FK_tokens_userId FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB CHARSET=utf8mb4;