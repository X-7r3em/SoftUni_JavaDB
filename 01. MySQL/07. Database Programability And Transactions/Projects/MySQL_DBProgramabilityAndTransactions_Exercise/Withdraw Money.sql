DELIMITER $$

CREATE PROCEDURE usp_withdraw_money(account_id INT, money_amount DECIMAL(19,4))
BEGIN
	IF money_amount > (SELECT a.balance FROM accounts AS a WHERE a.id = account_id) OR money_amount <= 0
		THEN ROLLBACK;
	ELSE
		UPDATE accounts AS a
        SET a.balance = a.balance - money_amount
        WHERE a.id = account_id;
    END IF;
END $$