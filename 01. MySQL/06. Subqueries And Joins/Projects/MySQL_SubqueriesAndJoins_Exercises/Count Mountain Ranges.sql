SELECT c.country_code,
	COUNT(m.mountain_range) AS mountain_range
FROM countries AS c
INNER JOIN mountains_countries AS mc
	ON c.country_code = mc.country_code
INNER JOIN mountains AS m
	ON mc.mountain_id = m.id
WHERE c.country_name IN ('United States', 'Bulgaria', 'Russia')
GROUP BY c.country_code
ORDER BY mountain_range DESC;