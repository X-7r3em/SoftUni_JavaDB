SELECT `deposit_group`, ROUND(SUM(`deposit_amount`), 2) AS `total_sum`
FROM `wizzard_deposits` AS wd
WHERE `magic_wand_creator` = 'Ollivander family'
GROUP BY wd.`deposit_group`
HAVING `total_sum` < 150000
ORDER BY `total_sum` DESC;