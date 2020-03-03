DELIMITER $$

CREATE PROCEDURE usp_raise_salaries(department_name VARCHAR(20))
BEGIN
	UPDATE employees AS e
    INNER JOIN departments AS d
    ON d.department_id = e.department_id
    SET e.salary = 1.05 * e.salary
    WHERE d.name = department_name;
END $$
