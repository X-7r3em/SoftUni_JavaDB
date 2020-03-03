DELIMITER $$

CREATE FUNCTION ufn_get_salary_level(salary DOUBLE)
RETURNS VARCHAR(45)
BEGIN
	RETURN CASE
		WHEN salary < 30000 THEN 'Low'
        WHEN salary <= 50000 THEN 'Average'
        ELSE 'High'
	END;
END $$