--liquibase formatted sql
--changeset marcinUzarek:6

CREATE TABLE user_intolerances (
  user_id bigint NOT NULL,
  product_id bigint NOT NULL,
  PRIMARY KEY (user_id,product_id),
  KEY Constr_userIntolerances_ProductId_FK (product_id),
  CONSTRAINT Constr_userIntolerances_User_fk FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT Constr_userIntolerances_ProductId_FK FOREIGN KEY (product_id) REFERENCES intolerances (id))
   ENGINE=InnoDB CHARSET=utf8mb4;