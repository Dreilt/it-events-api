--liquibase formatted sql
--changeset Dreilt:0011-1
UPDATE `app_user_roles`
SET role='ROLE_ORGANIZER' WHERE app_user_id = 4;