CREATE TABLE passports(
	passport_id INT PRIMARY KEY,
    passport_number VARCHAR(45) NOT NULL
);

INSERT INTO passports (passport_id, passport_number) VALUES
(101, 'N34FG21B'),
(102, 'K65LO4R7'),
(103, 'ZE657QP2');

CREATE TABLE persons (
	person_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(45) NOT NULL,
    salary DECIMAl(10,2) NOT NULL,
    passport_id INT NOT NULL UNIQUE
);

ALTER TABLE persons
ADD CONSTRAINT fk_persons_passports
    FOREIGN KEY (passport_id)
    REFERENCES passports(passport_id);

INSERT INTO persons(first_name, salary, passport_id) VALUES
('Roberto', 43300.00, 102),
('Tom', 56100.00, 103),
('Yana', 60200.00, 101);

