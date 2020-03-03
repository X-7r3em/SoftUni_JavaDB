DELIMITER $$

CREATE PROCEDURE usp_get_employees_from_town(town VARCHAR(45))
BEGIN
	SELECT  e.first_name, e.last_name
	FROM employees AS e
	INNER JOIN addresses AS a
	ON a.address_id = e.address_id
	INNER JOIN towns AS t
	ON a.town_id = t.town_id
	WHERE t.name = town
	ORDER BY e.first_name, e.last_name, e.employee_id;
END $$