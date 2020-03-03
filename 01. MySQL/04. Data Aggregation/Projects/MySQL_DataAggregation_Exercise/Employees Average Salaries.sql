CREATE TABLE `temporary_salaries` AS 
SELECT `e`.`department_id`, `e`.`salary`
FROM `employees` as `e`
WHERE `e`.`salary` > 30000 AND `e`.`manager_id` != 42;

UPDATE `temporary_salaries`
SET `salary` = 5000 + `salary`
WHERE `department_id` = 1;

SELECT `ts`.`department_id`, AVG(`ts`.`salary`) AS `avg_salary`
FROM `temporary_salaries` AS `ts`
GROUP BY `ts`.`department_id`
ORDER BY `ts`.`department_id`;
