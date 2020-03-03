INSERT INTO comments (content, user_id, post_id)
SELECT CONCAT('Omg!' , u.username, '!This is so cool!') ,
		CEILING(p.id * 3 / 2),
        p.id
FROM posts AS p
JOIN users AS u ON u.id = p.user_id
WHERE p.id BETWEEN 1 AND 10;