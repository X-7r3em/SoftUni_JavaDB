SELECT COUNT(e.employee_id) AS count
FROM employees AS e
WHERE e.salary > (SELECT AVG(e_in.salary) FROM employees AS e_in);