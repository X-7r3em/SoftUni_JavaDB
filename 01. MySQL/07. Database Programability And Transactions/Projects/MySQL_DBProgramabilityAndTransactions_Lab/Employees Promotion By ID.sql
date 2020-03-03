DELIMITER $$

CREATE PROCEDURE usp_raise_salary_by_id(id INT)
BEGIN
	START TRANSACTION;
    IF (SELECT COUNT(e.employee_id) FROM employees AS e WHERE e.employee_id = id) <> 1
		THEN ROLLBACK;
	ELSE
		UPDATE employees AS e
		SET e.salary = 1.05 * e.salary
		WHERE e.employee_id = id;
    END IF;
END $$