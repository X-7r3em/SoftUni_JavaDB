USE `hotel`;
CREATE TABLE `employees` (
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(45) NOT NULL,
`last_name` VARCHAR(45) NOT NULL,
`title` VARCHAR(45) NOT NULL,
`notes` TEXT
);

INSERT INTO `employees` (`first_name`, `last_name`, `title`) VALUES
('vasil', 'egov', 'geroi'),
('vasil', 'egov', 'geroi'),
('vasil', 'egov', 'geroi');

CREATE TABLE `customers` (
`account_number` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(45) NOT NULL,
`last_name` VARCHAR(45) NOT NULL,
`phone_number` VARCHAR(45) NOT NULL,
`emergency_name` VARCHAR(45) NOT NULL,
`emergency_number` VARCHAR(45) NOT NULL,
`notes` TEXT
);

INSERT INTO `customers` (`first_name`, `last_name`, `phone_number`, `emergency_name`, `emergency_number`) VALUES 
('vasil', 'egov', 'geroi', 'ABADA', 'ABADA'),
('vasil', 'egov', 'geroi', 'ABADA', 'ABADA'),
('vasil', 'egov', 'geroi', 'ABADA', 'ABADA');

CREATE TABLE `room_status` (
`room_status` VARCHAR(45) PRIMARY KEY,
`notes` TEXT
);

INSERT INTO `room_status` (`room_status`) VALUES
('Ready'),
('Nope'),
('Dont go there');

CREATE TABLE `room_types` (
`room_type` VARCHAR(45) PRIMARY KEY,
`notes` TEXT
);

INSERT INTO `room_types` (`room_type`) VALUES
('Big'),
('Small'),
('Wholehouse');

CREATE TABLE `bed_types` (
`bed_type` VARCHAR(45) PRIMARY KEY,
`notes` TEXT
);

INSERT INTO `bed_types` (`bed_type`) VALUES
('Big'),
('Small'),
('Wholehouse');

CREATE TABLE `rooms` (
`room_number` INT PRIMARY KEY,
`room_type` VARCHAR(45) NOT NULL,
`bed_type` VARCHAR(45) NOT NULL,
`rate` INT NOT NULL,
`room_status` BOOLEAN NOT NULL,
`notes` TEXT
);

INSERT INTO `rooms` (`room_number`, `room_type`, `bed_type`, `rate`, `room_status`) VALUES 
('1', 'Big', 'Big', '45', TRUE),
('2', 'Big', 'Big', '45', TRUE),
('3', 'Big', 'Big', '45', TRUE);

CREATE TABLE `payments` (
	`id` INT PRIMARY KEY AUTO_INCREMENT, 
    `employee_id` INT NOT NULL, 
    `payment_date` DATE NOT NULL, 
    `account_number` INT NOT NULL, 
    `first_date_occupied` DATE NOT NULL, 
    `last_date_occupied` DATE NOT NULL, 
    `total_days` INT NOT NULL, 
    `amount_charged` DOUBLE(10,2) NOT NULL, 
    `tax_rate` DOUBLE(10,2) NOT NULL, 
    `tax_amount` DOUBLE(10,2) NOT NULL, 
    `payment_total` DOUBLE(10,2) NOT NUll, 
    `notes` TEXT
);

INSERT INTO `payments` (`employee_id`, `payment_date`, `account_number`, `first_date_occupied`, `last_date_occupied`,
`total_days`, `amount_charged`, `tax_rate`, `tax_amount`, `payment_total`) VALUES
('1', '2000-01-01', '1', '2000-01-01', '2000-01-01', '2', '500', '20', '30', '20'),
('1', '2000-01-01', '1', '2000-01-01', '2000-01-01', '2', '500', '20', '30', '20'),
('1', '2000-01-01', '1', '2000-01-01', '2000-01-01', '2', '500', '20', '30', '20');

CREATE TABLE `occupancies` (
	`id` INT AUTO_INCREMENT PRIMARY KEY,  
	`employee_id` INT NOT NULL,
	`date_occupied` DATE NOT NULL,
	`account_number` INT NOT NULL, 
	`room_number` INT NOT NULL, 
	`rate_applied` DOUBLE(10,2) NOT NULL, 
	`phone_charge` DOUBLE(10,2) NOT NULL,
	`notes` TEXT
);

INSERT INTO `occupancies` (`employee_id`, `date_occupied`, `account_number`, 
`room_number`, `rate_applied`, `phone_charge`) VALUES 
('1', '2000-01-01', '1', '1', '1', '1'),
('1', '2000-01-01', '1', '1', '1', '1'),
('1', '2000-01-01', '1', '1', '1', '1');