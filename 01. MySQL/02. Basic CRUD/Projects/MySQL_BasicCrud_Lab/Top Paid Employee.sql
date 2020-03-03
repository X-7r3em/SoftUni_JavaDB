CREATE VIEW `v_top_paid_employee` AS
	SELECT *
	FROM `employees`
	WHERE `salary` = (SELECT MAX(`salary`) FROM `employees`);
	
SELECT * FROM `v_top_paid_employee`;    
    
SELECT *
FROM `employees`
ORDER BY `salary` DESC
LIMIT 1;