SELECT f.number AS flight_number,
       f.departure_time_scheduled,
       f.departure_time_actual,
       f.arrival_time_scheduled,
       f.arrival_time_actual,
       f.gate,
       a.type AS aircraft_type,
       al.name AS airline_name,
       ap.city AS destination_city
FROM tb_flight f
         JOIN tb_airport ap ON f.airport_to = ap.id
         JOIN tb_aircraft a ON f.aircraft_id = a.id
         JOIN tb_airline al ON a.airline = al.id
WHERE f.airport_from = 'JFK';
---
SELECT f.number AS flight_number,
       f.departure_time_scheduled,
       f.departure_time_actual,
       f.arrival_time_scheduled,
       f.arrival_time_actual,
       f.gate,
       a.type AS aircraft_type,
       al.name AS airline_name,
       ap.city AS origin_city,
       ap.id AS origin_airport_id
FROM tb_flight f
         JOIN tb_airport ap ON f.airport_from = ap.id
         JOIN tb_aircraft a ON f.aircraft_id = a.id
         JOIN tb_airline al ON a.airline = al.id
WHERE f.airport_to = 'JFK';