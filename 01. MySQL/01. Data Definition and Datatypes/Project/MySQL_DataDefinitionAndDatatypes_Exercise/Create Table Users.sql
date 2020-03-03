CREATE TABLE `users` (
`id` INT8 SIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
`username` VARCHAR(30) UNIQUE NOT NULL,
`password` VARCHAR(26) NOT NULL,
`profile_picture` BLOB,
`last_login_time` DATE,
`is_deleted` BOOLEAN
);

INSERT INTO `users` (`username`, `password`,`is_deleted`) VALUES
('Vasil12', 'Vasil', true),
('Vasil2', 'Vasil', true),
('Vasil3', 'Vasil', true),
('Vasil4', 'Vasil', true),
('Vasil5', 'Vasil', true);