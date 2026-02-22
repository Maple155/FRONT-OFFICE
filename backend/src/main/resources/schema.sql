CREATE TABLE Lieu (
    id SERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    libelle VARCHAR(255) NOT NULL
);

CREATE TABLE Reservation (
    id SERIAL PRIMARY KEY,
    id_lieu INT NOT NULL,
    client VARCHAR(100) NOT NULL,
    nbPassager INT NOT NULL,
    dateHeure TIMESTAMP NOT NULL,

    CONSTRAINT fk_reservation_lieu
        FOREIGN KEY (id_lieu)
        REFERENCES Lieu(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_reservation_dateHeure ON Reservation(dateHeure);
CREATE INDEX idx_reservation_client ON Reservation(client);
CREATE INDEX idx_reservation_lieu ON Reservation(id_lieu);

INSERT INTO Lieu (code, libelle) VALUES
('IVT', 'Ivato Aéroport'),
('TNR', 'Tanà Centre'),
('ANL', 'Analakely');

INSERT INTO Reservation (id_lieu, client, nbPassager, dateHeure) VALUES
(1, 'CLIENT-001', 2, '2024-01-15 14:30:00'),
(2, 'CLIENT-002', 4, '2024-01-16 10:00:00'),
(1, 'CLIENT-003', 1, '2024-01-17 16:45:00');