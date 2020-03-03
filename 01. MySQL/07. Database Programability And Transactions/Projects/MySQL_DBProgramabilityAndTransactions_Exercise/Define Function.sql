DELIMITER $$

CREATE FUNCTION ufn_is_word_comprised(set_of_letters VARCHAR(50), word VARCHAR(50))
RETURNS INT
BEGIN
	DECLARE result INT;
    SET result := (SELECT REGEXP_LIKE(word, CONCAT('^[', set_of_letters, ']*$')));
    RETURN result;
END $$