DELIMITER $$

CREATE PROCEDURE usp_transfer_money(from_account_id INT, to_account_id INT, amount DECIMAL(19,4)) 
BEGIN
	START TRANSACTION;
    CASE 
		WHEN (SELECT COUNT(id) FROM accounts WHERE from_account_id = id) <> 1 THEN ROLLBACK;
		WHEN (SELECT COUNT(id) FROM accounts WHERE to_account_id = id) <> 1 THEN ROLLBACK;
        WHEN amount <= 0 THEN ROLLBACK;
        WHEN (SELECT balance FROM accounts WHERE from_account_id = id) < amount THEN ROLLBACK;
        WHEN from_account_id = to_account_id THEN ROLLBACK;
        ELSE 
			UPDATE accounts AS a
			SET a.balance = a.balance - amount
			WHERE a.id = from_account_id;
			UPDATE accounts AS a
			SET a.balance = a.balance + amount
			WHERE a.id = to_account_id;
	END CASE;
END $$