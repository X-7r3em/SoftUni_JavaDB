INSERT INTO issues (title, issue_status, repository_id, assignee_id)
SELECT CONCAT('Critical Problem With ', f.name,'!') AS title,
	'open' AS issue_status,
    CEIL((f.id * 2) / 3) AS repository_id,
    c.contributor_id AS assignee_id
FROM files AS f
JOIN commits AS c
ON c.id = f.commit_id
WHERE f.id BETWEEN 46 AND 50;