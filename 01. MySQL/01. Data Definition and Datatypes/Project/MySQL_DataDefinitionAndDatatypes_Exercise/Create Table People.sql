CREATE TABLE `people` (
`id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
`name` VARCHAR(200) NOT NULL,
`picture` BLOB,
`height` DOUBLE (2,2),
`weight` DOUBLE (3,2),
`gender` CHAR(1) NOT NULL CHECK(`gender` = 'f' OR `gender` = 'm'),
`birthdate` DATE NOT NULL,
`biography` TEXT(65635)
);

INSERT INTO `people` (`name`, `gender`, `birthdate`) VALUES 
('Vasil', 'm', '2000-09-22'),
('Vasil', 'm', '2000-09-22'),
('Vasil', 'm', '2000-09-22'),
('Vasil', 'm', '2000-09-22'),
('Vasil', 'm', '2000-09-22');