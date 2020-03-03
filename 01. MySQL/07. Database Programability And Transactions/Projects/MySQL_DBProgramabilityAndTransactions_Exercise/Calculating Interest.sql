DELIMITER $$

CREATE FUNCTION ufn_calculate_future_value(initial_sum DOUBLE, yearly_interest_rate DECIMAL(10,4), years INT)
RETURNS DECIMAL(19,4)
BEGIN	
	DECLARE total_value DECIMAL(19,4);
    SET total_value := initial_sum * POW((1 + yearly_interest_rate), years);
    RETURN total_value;
END;

CREATE PROCEDURE usp_calculate_future_value_for_account(id INT, interest_rate DOUBLE)
BEGIN
	SELECT a.id AS account_id,
		ah.first_name,
        ah.last_name,
        a.balance AS current_balance,
        ufn_calculate_future_value(a.balance, interest_rate, 5) AS balance_in_5_years
    FROM account_holders AS ah
    INNER JOIN accounts AS a
    ON a.account_holder_id = ah.id
    WHERE a.id = id;
END $$
