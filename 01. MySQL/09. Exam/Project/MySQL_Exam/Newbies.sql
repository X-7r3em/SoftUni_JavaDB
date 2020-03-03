SELECT e.id, 
	CONCAT(e.first_name, ' ', last_name) AS full_name,
    CONCAT('$', salary) AS salary,
    started_on
FROM employees AS e
WHERE e.salary >= 100000 AND YEAR(started_on) >= 2018
ORDER BY e.salary DESC, e.id;