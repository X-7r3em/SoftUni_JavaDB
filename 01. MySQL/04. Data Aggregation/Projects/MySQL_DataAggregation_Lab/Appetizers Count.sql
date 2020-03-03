SELECT COUNT(p.`category_id`) AS `count`
FROM `products` AS p
WHERE p.`price` > 8
GROUP BY p.`category_id`
HAVING p.`category_id` = 2
ORDER BY p.`category_id`;