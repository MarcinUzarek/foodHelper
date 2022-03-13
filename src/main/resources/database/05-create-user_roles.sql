--liquibase formatted sql
--changeset marcinUzarek:5

CREATE TABLE user_roles (
  user_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (user_id,role_id),
  KEY Constr_userRoles_RoleId_FK (role_id),
  CONSTRAINT Constr_userRoles_RoleId_FK FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT Constr_userRoles_UserId_FK FOREIGN KEY (user_id) REFERENCES users (id))
   ENGINE=InnoDB CHARSET=utf8mb4;