SELECT SUM(
	(SELECT `deposit_amount` - (SELECT `deposit_amount` FROM `wizzard_deposits` WHERE wd.`id` + 1 = `id`)
	FROM `wizzard_deposits` AS wd
    WHERE wde.`id` = wd.`id`)
) AS 'sum_difference'
FROM `wizzard_deposits` AS wde;