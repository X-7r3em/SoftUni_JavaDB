SELECT ca.id, 
	CONCAT(ca.card_number, ' : ', c.full_name)AS card_token
FROM clients AS c
JOIN bank_accounts AS ba
ON ba.client_id = c.id
JOIN cards AS ca
ON ca.bank_account_id = ba.id
ORDER BY ca.id DESC;