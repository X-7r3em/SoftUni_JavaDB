DELIMITER $$

CREATE FUNCTION ufn_is_word_comprised(set_of_letters VARCHAR(50), word VARCHAR(50))
RETURNS INT
BEGIN
	DECLARE word_ind INT;
    SET word_ind := 1;
    WHILE (word_ind <= CHAR_LENGTH(word)) DO
		IF LOCATE(SUBSTRING(word, word_ind, 1), set_of_letters) = 0 
        THEN RETURN 0;
        END IF;
	SET word_ind := word_ind + 1;
    END WHILE;
    RETURN 1;
END $$