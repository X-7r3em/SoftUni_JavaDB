USE `soft_uni`;
CREATE TABLE `towns` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(45) NOT NULL
);

CREATE TABLE `addresses` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `address_text` TEXT NOT NULL,
    `town_id` INT NOT NULL,
    CONSTRAINT `fk_addresses_towns`
		FOREIGN KEY (`town_id`)
        REFERENCES `towns`(`id`)
);

CREATE TABLE `departments` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL
);

CREATE TABLE `employees` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
    `first_name` VARCHAR(45) NOT NULL, 
    `middle_name` VARCHAR(45) NOT NULL, 
    `last_name` VARCHAR(45) NOT NULL, 
    `job_title` VARCHAR(45) NOT NULL, 
    `department_id` INT NOT NULL, 
    `hire_date` DATE NOT NULL, 
    `salary` DOUBLE (10,2), 
    `address_id` INT,
    CONSTRAINT `fk_employees_department`
		FOREIGN KEY (`department_id`)
        REFERENCES `departments`(`id`),
	CONSTRAINT `fk_employees_address`
		FOREIGN KEY (`address_id`)
        REFERENCES `addresses`(`id`)
);