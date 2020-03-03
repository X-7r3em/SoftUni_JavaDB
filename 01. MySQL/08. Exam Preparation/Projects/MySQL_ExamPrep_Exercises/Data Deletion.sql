DELETE FROM colonists
WHERE id IN (
	SELECT i.id FROM (
		SELECT c.id 
		FROM colonists AS c
		LEFT JOIN travel_cards AS tc ON tc.colonist_id = c.id
		WHERE tc.journey_id IS NULL) AS i
);