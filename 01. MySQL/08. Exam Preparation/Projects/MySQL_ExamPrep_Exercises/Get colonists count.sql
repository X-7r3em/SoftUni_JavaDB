DELIMITER $$
CREATE FUNCTION udf_count_colonists_by_destination_planet (planet_name VARCHAR (30)) 
RETURNS INT
BEGIN
	DECLARE count_colonist INT;
    SET count_colonist = (
		SELECT COUNT(c.id) 
		FROM colonists AS c
		JOIN travel_cards AS tc ON tc.colonist_id = c.id
		JOIN journeys AS j ON tc.journey_id = j.id
		JOIN spaceports AS sp ON j.destination_spaceport_id = sp.id
		JOIN planets AS p ON p.id = sp.planet_id
		WHERE p.name = planet_name
		);
		RETURN count_colonist;
END; $$

