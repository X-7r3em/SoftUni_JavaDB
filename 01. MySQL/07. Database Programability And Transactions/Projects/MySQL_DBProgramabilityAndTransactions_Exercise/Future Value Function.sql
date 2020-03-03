DELIMITER $$

CREATE FUNCTION ufn_calculate_future_value(initial_sum DOUBLE, yearly_interest_rate DOUBLE, years INT)
RETURNS DOUBLE
BEGIN	
	DECLARE total_value DOUBLE;
    SET total_value := initial_sum * POW((1 + yearly_interest_rate), years);
    RETURN total_value;
END $$