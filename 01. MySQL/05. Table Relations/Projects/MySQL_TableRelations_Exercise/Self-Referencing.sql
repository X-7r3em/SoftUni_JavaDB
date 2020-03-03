CREATE TABLE teachers(
teacher_id	INT PRIMARY KEY,
`name` VARCHAR(45),
manager_id INT NULL,
CONSTRAINT fk_teachers_teachers
FOREIGN KEY (manager_id)
REFERENCES teachers(teacher_id)
);

INSERT INTO teachers(teacher_id, `name`, manager_id) VALUES 
(101, 'John', null),
(106, 'Greta', 101),
(102, 'Maya', 106),
(103, 'Silvia', 106),
(105, 'Mark', 101),
(104, 'Ted', 105);

SELECT * FROM teachers;

