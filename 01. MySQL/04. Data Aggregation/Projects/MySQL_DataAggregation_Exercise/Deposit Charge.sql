SELECT wd.`deposit_group`, wd.`magic_wand_creator`, ROUND(MIN(wd.`deposit_charge`), 2) AS `min_deposit_charge`
FROM `wizzard_deposits` AS wd
GROUP BY wd.`deposit_group`, wd.`magic_wand_creator`
ORDER BY wd.`magic_wand_creator`, wd.`deposit_group`;