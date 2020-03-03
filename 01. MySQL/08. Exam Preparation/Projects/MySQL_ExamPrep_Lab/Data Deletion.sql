DELETE FROM repositories
WHERE repositories.id NOT IN (SELECT i.repository_id FROM issues AS i);