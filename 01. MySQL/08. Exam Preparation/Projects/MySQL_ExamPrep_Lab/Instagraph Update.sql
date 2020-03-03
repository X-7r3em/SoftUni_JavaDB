UPDATE users AS u
SET u.profile_picture_id = (
	SELECT IF(u2.follower_count > 0, u2.follower_count, u.id)
    FROM (
		SELECT u3.id AS id, COUNT(uf.follower_id) AS follower_count
		FROM users AS u3
		LEFT JOIN users_followers AS uf ON uf.user_id = u3.id
		GROUP BY u3.id) AS u2
    WHERE u.id = u2.id
    )
    
WHERE u.profile_picture_id IS NULL;