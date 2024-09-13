--liquibase formatted sql
--changeset id:1588510956470-1
CREATE TABLE IF NOT EXISTS person.person(
    id            UUID         NOT NULL CONSTRAINT PK_person PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    address       VARCHAR(255),
    country       VARCHAR(2) NOT NULL,
    job_title     VARCHAR(255) NOT NULL,
    annual_salary NUMERIC(19, 2),
    employer      VARCHAR(255),
    gender        VARCHAR(255) NOT NULL DEFAULT 'NA'
);

--liquibase formatted sql
--changeset id:1588510956471-1
INSERT INTO person.person (id, name, surname, date_of_birth, address, country, job_title, annual_salary, employer, gender)
VALUES (gen_random_uuid(), 'Brad', 'Pitt', '1963-12-18', '123 Main St, Anytown', 'US', 'Actor', 100000.00, 'Hollywood', 'MAN');


--liquibase formatted sql
--changeset id:1588510956471-2
INSERT INTO person.person (id, name, surname, date_of_birth, address, country, job_title, annual_salary, employer, gender)
VALUES (gen_random_uuid(), 'Natalie', 'Portman', '1981-06-09', '123 Main St, Anytown', 'US', 'Actress', 100000.00, 'Hollywood', 'WOMAN');


