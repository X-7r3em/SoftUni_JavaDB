DELIMITER $$

CREATE PROCEDURE udp_clientinfo(full_name VARCHAR(50))
BEGIN
	SELECT c.full_name, c.age, ba.account_number, CONCAT('$', ba.balance) AS balance
	FROM clients AS c
	JOIN bank_accounts AS ba ON ba.client_id = c.id
    WHERE full_name = c.full_name;
END; $$

DELIMITER ;

CALL udp_clientinfo('Hunter Wesgate');
