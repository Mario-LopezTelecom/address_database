CREATE DATABASE exercise;
USE exercise;
CREATE TABLE contact (
	contact_id SMALLINT NOT NULL AUTO_INCREMENT,
	firstname VARCHAR(20) NOT NULL,
	lastname VARCHAR(20) NOT NULL,
	address VARCHAR(20),
	email VARCHAR(100) NOT NULL,
	phone VARCHAR(15),
	PRIMARY KEY (contact_id)
);

CREATE USER 'java'@'localhost' IDENTIFIED BY 'java';
GRANT ALL PRIVILEGES ON exercise.contact TO 'java'@'localhost';
