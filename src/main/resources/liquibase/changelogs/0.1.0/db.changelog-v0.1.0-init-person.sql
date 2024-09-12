--liquibase formatted sql
--changeset id:1588510956470-1
CREATE TABLE IF NOT EXISTS person.person(
    id            UUID         NOT NULL CONSTRAINT PK_person PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    country       VARCHAR(2) NOT NULL,
    job_title     VARCHAR(255) NOT NULL,
    address       VARCHAR(255),
    status        VARCHAR(255) NOT NULL DEFAULT 'READY_FOR_INTERVIEW',
    employer      VARCHAR(255),
    gender        VARCHAR(255) NOT NULL DEFAULT 'WOMAN'
);

--liquibase formatted sql
--changeset id:1588510956471-1
INSERT INTO person.person (id, name, surname, date_of_birth, country, job_title, address, status, employer, gender)
VALUES (gen_random_uuid(), 'Brad', 'Pitt', '1980-01-01', 'US', 'Actor', null, 'READY_FOR_INTERVIEW', 'Abn-Amro', 'MAN');


--liquibase formatted sql
--changeset id:1588510956471-2
INSERT INTO person.person (id, name, surname, date_of_birth, country, job_title, address, status, employer, gender)
VALUES (gen_random_uuid(), 'Brad', 'Pitt', '1980-01-01', 'US', 'Actor', null, 'READY_FOR_INTERVIEW', 'Abn-Amro', 'MAN');


