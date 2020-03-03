CREATE TABLE `categories`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`category` VARCHAR(45) NOT NULL,
`daily_rate` DOUBLE(4,2) NOT NULL,
`weekly_rate` DOUBLE(4,2) NOT NULL,
`monthly_rate` DOUBLE(4,2) NOT NULL,
`weekend_rate` DOUBLE(4,2) NOT NULL
);

INSERT INTO `categories` (`category`, `daily_rate`, `weekly_rate`, `monthly_rate`, `weekend_rate`) VALUES
('Cat', '32.0', '32.0', '32.0', '32.0'),
('Cat', '32.0', '32.0', '32.0', '32.0'),
('Cat', '32.0', '32.0', '32.0', '32.0');

CREATE TABLE `cars`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`category` VARCHAR(45) NOT NULL,
`make` VARCHAR(45) NOT NULL,
`model` VARCHAR(45) NOT NULL,
`car_year` DATE NOT NULL,
`category_id` INT NOT NULL,
`doors` INT NOT NULL,
`picture` BLOB,
`car_condition` TEXT,
`availible` INT1 NOT NULL
);

INSERT INTO `cars` (`category`, `make`, `model`, `car_year`, `category_id`, `doors`, `availible`) VALUES
('Cat', 'Cat', 'Cat', '2000-01-01', '1', '1', TRUE),
('Cat', 'Cat', 'Cat', '2000-01-01', '1', '1', TRUE),
('Cat', 'Cat', 'Cat', '2000-01-01', '1', '1', TRUE);


CREATE TABLE `employees`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(45) NOT NULL,
`last_name` VARCHAR(45) NOT NULL,
`title` VARCHAR(45) NOT NULL,
`notes` TEXT
);

INSERT INTO `employees` (`first_name`, `last_name`, `title`) VALUES
('Cat', 'Cat', 'Cat'),
('Cat', 'Cat', 'Cat'),
('Cat', 'Cat', 'Cat');

CREATE TABLE `customers`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`driver_licence_number` VARCHAR(45) NOT NULL,
`full_name` VARCHAR(45) NOT NULL,
`address` VARCHAR(45) NOT NULL,
`city` VARCHAR(45) NOT NULL,
`zip_code` VARCHAR(45) NOT NULL,
`notes` TEXT
);

INSERT INTO `customers` (`driver_licence_number`, `full_name`, `address`, `city`, `zip_code`) VALUES
('Cat', 'Cat', 'Cat', 'Cat', 'Cat'),
('Cat', 'Cat', 'Cat', 'Cat', 'Cat'),
('Cat', 'Cat', 'Cat', 'Cat', 'Cat');

CREATE TABLE `rental_orders`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`employee_id` INT NOT NULL,
`customer_id` INT NOT NULL,
`car_id` INT NOT NULL,
`car_condition` TEXT,
`tank_level` DOUBLE(4,2) NOT NULL,
`kilometrage_start` INT NOT NULL,
`kilometrage_end` INT NOT NULL,
`total_kilometrage` INT NOT NULL,
`start_date` DATE NOT NULL,
`end_date` DATE NOT NULL,
`total_days` INT NOT NULL,
`rate_applied` VARCHAR(45) NOT NULL,
`tax_rate` DOUBLE(4,2),
`order_status` INT1 NOT NULL,
`notes` TEXT
);

INSERT INTO `rental_orders` (`employee_id`, `customer_id`, `car_id`, `tank_level`,
 `kilometrage_start`, `kilometrage_end`, `total_kilometrage`, `start_date`,
 `end_date` ,`total_days`, `rate_applied` , `tax_rate`, `order_status`)
VALUES
('1', '1', '1', '4.2', '1', '1', '1', '2000-01-01', '2000-01-01', '1','Week' ,'4.2', TRUE),
('1', '1', '1', '1', '1', '1', '1', '2000-01-01', '2000-01-01', '1','1' ,'1.2', TRUE),
('1', '1', '1', '1', '1', '1', '1', '2000-01-01', '2000-01-01', '1','1' ,'1.2', FALSE);
