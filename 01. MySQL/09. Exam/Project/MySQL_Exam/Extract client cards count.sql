DELIMITER $$

CREATE FUNCTION udf_client_cards_count(name VARCHAR(30))
RETURNS INT
BEGIN
	DECLARE count_cards INT(11);
    SET count_cards = (
		SELECT COUNT(c.id) AS cards
		FROM cards AS ca
		JOIN bank_accounts AS ba ON ca.bank_account_id = ba.id
		JOIN clients AS c ON c.id = ba.client_id
		WHERE c.full_name = name
        );
    RETURN count_cards;
END; $$

SELECT udf_client_cards_count('Baxy David')$$