DELIMITER $$
DROP PROCEDURE udp_commit$$
CREATE PROCEDURE udp_commit(
	username VARCHAR(30),
	password VARCHAR(30),
	message VARCHAR(255),
	issue_id INT
)
BEGIN
	START TRANSACTION;
    IF (SELECT COUNT(u.username) FROM users AS u WHERE u.username = username) <> 1 
		THEN 
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No such user!';
        ROLLBACK;
    ELSEIF (SELECT u.password FROM users AS u WHERE u.username = username) != password
		THEN
		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Password is incorrect!';
        ROLLBACK;
	ELSEIF (SELECT COUNT(i.id) FROM issues AS i WHERE i.assignee_id = issue_id) = 0 
		THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'The issue does not exist!';
        ROLLBACK;
	ELSE 
        INSERT INTO commits (message, issue_id, repository_id, contributor_id)
        VALUES (
			message,
			issue_id,
            (SELECT repository_id FROM issues WHERE issue_id = issues.id LIMIT 1),
            (SELECT id FROM users WHERE username = users.username)
            );
            
            UPDATE issues
            SET issue_status = 'closed'
            WHERE issues.id = issue_id;
            COMMIT;
    END IF;
END; $$

DELIMITER ;
CALL udp_commit('WhoDenoteBel', 'ajmISQi*', 'Fixed issue: Invalid welcoming message in READ.html', 2);

SELECT * FROM commits
WHERE id > 50;