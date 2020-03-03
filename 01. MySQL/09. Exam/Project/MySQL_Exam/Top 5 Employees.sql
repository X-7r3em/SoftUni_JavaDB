SELECT CONCAT(e.first_name, ' ', e.last_name) AS name,
	e.started_on,
    COUNT(e.id) AS count_of_clients
FROM employees AS e
JOIN employees_clients AS ec
ON ec.employee_id = e.id
GROUP BY e.id
ORDER BY count_of_clients DESC, e.id
LIMIT 5;