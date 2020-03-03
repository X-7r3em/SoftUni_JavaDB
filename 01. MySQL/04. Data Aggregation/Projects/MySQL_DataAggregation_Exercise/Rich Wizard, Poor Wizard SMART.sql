SELECT 
	(SELECT `deposit_amount` FROM `wizzard_deposits` ORDER BY `id` LIMIT 1) - 
	(SELECT `deposit_amount` FROM `wizzard_deposits` ORDER BY `id` DESC LIMIT 1) 
AS 'sum_difference';