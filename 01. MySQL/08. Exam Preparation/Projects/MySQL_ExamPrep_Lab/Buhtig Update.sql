UPDATE repositories_contributors AS rc
SET rc.repository_id = (
		SELECT r.id
        FROM repositories AS r
		WHERE r.id NOT IN(SELECT ri.repository_id FROM (SELECT repository_id FROM repositories_contributors) AS ri)
        ORDER BY r.id
        LIMIT 1)
WHERE rc.contributor_id = rc.repository_id;