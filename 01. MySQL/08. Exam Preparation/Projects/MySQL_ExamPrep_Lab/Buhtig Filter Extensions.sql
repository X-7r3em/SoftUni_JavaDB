DELIMITER $$
DROP PROCEDURE udp_findbyextension$$
CREATE PROCEDURE udp_findbyextension(extension VARCHAR(50))
BEGIN
	SELECT f.id, f.name, CONCAT(f.size, 'KB') AS size
    FROM files AS f
    WHERE name LIKE CONCAT('%.', extension)
    ORDER BY f.id;
END; $$

DELIMITER ;
CALL udp_findbyextension('html');