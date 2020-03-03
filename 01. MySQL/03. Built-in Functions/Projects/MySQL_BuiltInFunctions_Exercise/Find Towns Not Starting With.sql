	SELECT `town_id`, `name` FROM `towns`
    WHERE LOWER(LEFT(`name`, 1)) REGEXP '[^rbd]'
    ORDER BY `name`;