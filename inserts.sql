INSERT INTO airline (id, name)
VALUES (1, 'Acme Airlines'),
       (2, 'Budget Air'),
       (3, 'International Skyways');

INSERT INTO manufacturer (id, name)
VALUES (1, 'Boeing'),
       (2, 'Airbus'),
       (3, 'Embraer');

INSERT INTO airport (id, name, city, country)
VALUES ('SDU', 'Santos Dumont Airport', 'Rio de Janeiro', 'Brazil'),
       ('GRU', 'Guarulhos International Airport', 'São Paulo', 'Brazil'),
       ('CDG', 'Charles de Gaulle Airport', 'Paris', 'France');

INSERT INTO aircraft (id, type, airline, manufacturer, registration, max_passengers)
VALUES (1, '737-800', 1, 1, 'ACME123', 189),
       (2, 'A320neo', 2, 2, 'BUDGET456', 180),
       (3, 'E190-E2', 3, 1, 'ISKY789', 150);

INSERT INTO passenger (id, first_name, last_name, passport_number)
VALUES (1, 'Joana', 'Silva', 'BR123456789'),
       (2, 'Miguel', 'Souza', 'BR987654321');

-- Inserindo voos (exemplo com alguns voos)

INSERT INTO flight (number, airport_from, airport_to, departure_time_scheduled, departure_time_actual, arrival_time_scheduled, arrival_time_actual, gate, aircraft, connects_to)
VALUES ('AC123', 'SDU', 'GRU', '2024-06-03 10:00:00', NULL, '2024-06-03 10:30:00', NULL, 1, 1, NULL),
       ('BA456', 'GRU', 'CDG', '2024-06-03 14:00:00', NULL, '2024-06-03 20:00:00', NULL, 2, 2, 'AC123'),
       ('IS789', 'CDG', 'SDU', '2024-06-04 12:00:00', NULL, '2024-06-04 18:00:00', NULL, 3, 3, NULL);

-- Inserindo reservas (exemplo com algumas reservas)

INSERT INTO booking (id, flight, passenger, seat)
VALUES (1, 'AC123', 1, '1A'),
       (2, 'BA456', 2, '2B'),
       (3, 'IS789', 1, '3C');