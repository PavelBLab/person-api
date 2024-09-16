--liquibase formatted sql
--changeset id:1588510956480-0
ALTER TABLE person.person
    ALTER COLUMN gender TYPE VARCHAR(5) USING gender::VARCHAR(5),
    ALTER COLUMN gender SET DEFAULT 'NA';
