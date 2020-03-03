SELECT c.continent_code,
	c.currency_code,
    COUNT(c.country_code) AS currency_usage
FROM countries AS c
GROUP BY c.continent_code, c.currency_code
HAVING currency_usage > 1 
	AND currency_usage = 
		(
		SELECT COUNT(c_in.currency_code) AS currency_usage 
		FROM countries AS c_in
		WHERE c_in.continent_code = c.continent_code			
		GROUP BY c_in.currency_code
        ORDER BY currency_usage DESC
        LIMIT 1
		)
ORDER BY c.continent_code, c.currency_code;
