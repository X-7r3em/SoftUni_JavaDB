DELIMITER $$

CREATE PROCEDURE usp_get_holders_with_balance_higher_than(balance_amount DOUBLE)
BEGIN
	SELECT ah.first_name, ah.last_name
    FROM account_holders AS ah
    JOIN accounts AS a
    ON a.account_holder_id = ah.id
    GROUP BY ah.id
    HAVING SUM(a.balance) > balance_amount
    ORDER BY a.id;
END $$
