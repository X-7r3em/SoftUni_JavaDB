SELECT e.employee_id, 
	e.first_name
FROM employees AS e
LEFT JOIN employees_projects AS e_p
	ON e.employee_id = e_p.employee_id
WHERE e.employee_id NOT IN
	(SELECT e_p_in.employee_id 
    FROM employees_projects AS e_p_in)
ORDER BY e.employee_id DESC
LIMIT 3;