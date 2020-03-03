SELECT e.department_id, MAX(e.salary) AS 'max_salary'
FROM employees AS e
GROUP BY e.department_id
HAVING max_salary < 30000 OR max_salary > 70000
ORDER BY e.department_id; 