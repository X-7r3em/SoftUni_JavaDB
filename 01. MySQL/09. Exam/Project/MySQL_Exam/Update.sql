UPDATE clients AS c
JOIN employees_clients as ec
ON ec.client_id = c.id

SET ec.employee_id = (
	SELECT ec1.employee_id 
    FROM (SELECT employee_id, client_id FROM employees_clients) AS ec1
	GROUP BY ec1.employee_id
	ORDER BY COUNT(ec1.employee_id), ec1.employee_id
	LIMIT 1)
    
WHERE ec.client_id = ec.employee_id;

