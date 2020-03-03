DELIMITER $$

CREATE PROCEDURE usp_deposit_money(account_id INT, money_amount DECIMAL(19.4))
BEGIN
	START TRANSACTION;
    UPDATE accounts AS a
		SET a.balance = a.balance + money_amount
		WHERE a.id = account_id;
    IF (SELECT balance FROM accounts WHERE id = account_id) < 0 
		THEN 
		ROLLBACK;
    ELSE
		COMMIT;
	END IF;
END $$