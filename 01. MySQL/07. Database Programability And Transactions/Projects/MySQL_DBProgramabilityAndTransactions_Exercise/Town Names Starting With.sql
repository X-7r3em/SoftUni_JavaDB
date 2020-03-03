DELIMITER $$

CREATE PROCEDURE usp_get_towns_starting_with(text VARCHAR(50))
BEGIN
	SELECT t.name 
	FROM towns AS t
	WHERE name LIKE CONCAT(text,'%')
    ORDER BY t.name;
END $$