SELECT s.name, s.manufacturer
FROM spaceships AS s
JOIN journeys AS j ON s.id = j.spaceship_id
JOIN travel_cards AS tc ON j.id = tc.journey_id
JOIN colonists AS c ON tc.colonist_id = c.id
WHERE DATE_ADD(c.birth_date, INTERVAL 30 YEAR) > '2019-01-01' AND tc.job_during_journey = 'Pilot'
ORDER BY s.name;