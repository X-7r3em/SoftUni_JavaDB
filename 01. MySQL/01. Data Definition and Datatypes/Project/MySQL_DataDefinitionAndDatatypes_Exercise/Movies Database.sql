CREATE TABLE `directors`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`director_name` VARCHAR(45) NOT NULL,
`notes` TEXT
);
CREATE TABLE `genres`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`genre_name` VARCHAR(45) NOT NULL,
`notes` TEXT
);
CREATE TABLE `categories`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`category_name` VARCHAR(45) NOT NULL,
`notes` TEXT
);
CREATE TABLE `movies`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`title` VARCHAR(45) NOT NULL,
`director_id` INT NOT NULL,
`copyright_year` DATE NOT NULL,
`length` INT NOT NULL,
`genre_id` INT NOT NULL,
`category_id` INT NOT NULL,
`rating` DOUBLE(3,2),
`notes` TEXT
);

INSERT INTO `directors` (`director_name`) VALUES
('Vasil'),
('Vasil'),
('Vasil'),
('Vasil'),
('Vasil')
;

INSERT INTO `genres` (`genre_name`) VALUES 
('Action'),
('Action'),
('Action'),
('Action'),
('Action');

INSERT INTO `categories` (`category_name`) VALUES 
('Sick'),
('Sick'),
('Sick'),
('Sick'),
('Sick');

INSERT INTO `movies` (`title`, `director_id`, `copyright_year`, `length`, `genre_id`, `category_id`, `rating`) 
VALUES
('Title', '1', '2000-01-01', '100', '1', '1', '9.85'),
('Title', '1', '2000-01-01', '100', '1', '1', '9.85'),
('Title', '1', '2000-01-01', '100', '1', '1', '9.85'),
('Title', '1', '2000-01-01', '100', '1', '1', '9.85'),
('Title', '1', '2000-01-01', '100', '1', '1', '9.85');