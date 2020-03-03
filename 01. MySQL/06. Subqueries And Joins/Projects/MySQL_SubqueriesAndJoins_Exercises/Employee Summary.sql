SELECT e.employee_id, 
	CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
    CONCAT(e_m.first_name, ' ', e_m.last_name) AS manager_name,
    d.`name` AS department_name
FROM employees AS e
INNER JOIN departments AS d
	ON e.department_id = d.department_id
INNER JOIN employees AS e_m
	ON e.manager_id = e_m.employee_id
ORDER BY e.employee_id
LIMIT 5;