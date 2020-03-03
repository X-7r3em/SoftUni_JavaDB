DELETE FROM users
WHERE users.id IN (SELECT i.id FROM (SELECT u.id
FROM users AS u
LEFT JOIN users_followers AS uf ON u.id = uf.user_id
WHERE uf.follower_id IS NULL
UNION
SELECT u.id
FROM users_followers AS uf
LEFT JOIN users AS u ON u.id = uf.follower_id
WHERE uf.follower_id IS NULL) AS i);

