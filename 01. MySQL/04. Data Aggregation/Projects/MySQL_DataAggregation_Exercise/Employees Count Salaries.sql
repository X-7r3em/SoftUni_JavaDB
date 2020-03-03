SELECT COUNT(e.employee_id) 
FROM employees AS e
WHERE e.manager_id IS NULL;