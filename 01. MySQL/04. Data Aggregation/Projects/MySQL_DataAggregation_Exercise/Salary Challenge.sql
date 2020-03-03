SELECT e.first_name, e.last_name, e.department_id
FROM employees AS e
WHERE (SELECT AVG(salary) FROM employees WHERE department_id = e.department_id) < e.salary
ORDER BY e.department_id, e.employee_id
LIMIT 10;