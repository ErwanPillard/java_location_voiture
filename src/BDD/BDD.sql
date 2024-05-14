-- Création de la table Model.User
CREATE TABLE User
(
    id         INT AUTO_INCREMENT NOT NULL,
    email      VARCHAR(255) NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

-- Création de la table Model.Employe qui hérite de Model.User
CREATE TABLE Employe
(
    id       INT          NOT NULL,
    nom      VARCHAR(255) NOT NULL,
    prenom   VARCHAR(255) NOT NULL,
    fonction VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_employe_user FOREIGN KEY (id) REFERENCES User (id)
) ENGINE = InnoDB;

-- Création de la table Model.Client qui hérite de Model.User
CREATE TABLE Client
(
    id        INT AUTO_INCREMENT NOT NULL,
    telephone VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_client_user FOREIGN KEY (id) REFERENCES User (id)
) ENGINE = InnoDB;

-- Création de la table Entreprise qui hérite de Model.Client
CREATE TABLE Entreprise
(
    id          INT AUTO_INCREMENT NOT NULL,
    nom         VARCHAR(255) NOT NULL,
    numeroSiret VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_entreprise_user FOREIGN KEY (id) REFERENCES Client (id)
) ENGINE = InnoDB;

-- Création de la table Particulier qui hérite de Model.Client
CREATE TABLE Particulier
(
    id           INT          NOT NULL,
    nom          VARCHAR(255) NOT NULL,
    prenom       VARCHAR(255) NOT NULL,
    numeroPermis VARCHAR(255) NOT NULL UNIQUE,
    birthDate    DATE         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_particulier_user FOREIGN KEY (id) REFERENCES Client (id)
) ENGINE = InnoDB;

-- Création de la table Model.Modele de voiture
CREATE TABLE Modele
(
    id               INT AUTO_INCREMENT NOT NULL,
    marque           VARCHAR(255) NOT NULL,
    nom              VARCHAR(255) NOT NULL,
    nbPlaces         INT          NOT NULL,
    nbPortes         INT          NOT NULL,
    tailleCoffre     FLOAT        NOT NULL,
    caracteristique  VARCHAR(255),
    prixJournalier   INT          NOT NULL,
    noteSatisfaction INT,
    categorie        ENUM ('Citadine', 'Berline', 'SUV', 'Familiale', 'Utilitaire') NOT NULL,
    attelage         ENUM ('Oui', 'Non') NOT NULL,
    boiteVitesse     ENUM ('automatique', 'manuelle') NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

-- Création de la table Model.Voiture
CREATE TABLE Voiture
(
    immatriculation       VARCHAR(255) NOT NULL UNIQUE,
    dateMiseEnCirculation DATE         NOT NULL,
    nbKilometre DOUBLE NOT NULL,
    couleur               VARCHAR(255) NOT NULL,
    modele_id             INT          NOT NULL,
    image                 LONGBLOB,
    PRIMARY KEY (immatriculation),
    CONSTRAINT fk_voiture_modele FOREIGN KEY (modele_id) REFERENCES Modele (id) -- Référence correcte à la clé primaire de Model.Modele
) ENGINE = InnoDB;

-- Création de la table Model.Facture
CREATE TABLE Facture
(
    numeroFacture           INT AUTO_INCREMENT NOT NULL,
    dateEmission            DATE         NOT NULL,
    dateDebutReservation    DATE         NOT NULL,
    dateFinReservation      DATE         NOT NULL,
    montant                 FLOAT        NOT NULL,
    etat                    VARCHAR(255) NOT NULL,
    voiture_immatriculation VARCHAR(255) NOT NULL,
    id_client               INT          NOT NULL,
    PRIMARY KEY (numeroFacture)
) ENGINE = InnoDB;

-- Création de la table Model.Reservation
CREATE TABLE Reservation
(
    numReservation          INT AUTO_INCREMENT NOT NULL,
    dateDebutReservation    DATE         NOT NULL,
    dateFinReservation      DATE         NOT NULL,
    montant                 FLOAT        NOT NULL,
    etat                    VARCHAR(255) NOT NULL,
    voiture_immatriculation VARCHAR(255) NOT NULL,
    id_client               INT          NOT NULL,
    PRIMARY KEY (numReservation),
    CONSTRAINT fk_reservation_voiture FOREIGN KEY (voiture_immatriculation) REFERENCES Voiture (immatriculation),
    CONSTRAINT fk_reservation_client FOREIGN KEY (id_client) REFERENCES Client (id)
) ENGINE = InnoDB;


-- Création de la table Model.OffreReduction
CREATE TABLE OffreReduction
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    nom                  VARCHAR(255) NOT NULL,
    description          TEXT,
    dateDebut            DATE         NOT NULL,
    dateFin              DATE         NOT NULL,
    pourcentageReduction FLOAT        NOT NULL,
    typeAdhesion         ENUM('NOUVEAUCLIENT', 'BRONZE', 'ARGENT', 'OR') NOT NULL
)ENGINE = InnoDB;

-- Insérer des modèles de voiture
INSERT INTO Modele (marque, nom, nbPlaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage, boiteVitesse)
VALUES
    ('Ford', 'Fiesta', 5, 5, 300.0, 'Compacte', 50, NULL, 'Citadine', 'Non', 'manuelle'),
    ('Toyota', 'Corolla', 5, 5, 350.0, 'Hybride', 60, NULL, 'Berline', 'Oui', 'automatique'),
    ('Renault', 'Captur', 5, 5, 400.0, 'SUV', 70, NULL, 'SUV', 'Non', 'manuelle'),
    ('Volkswagen', 'Golf', 5, 5, 350.0, 'Polyvalente', 60, NULL, 'Berline', 'Oui', 'manuelle'),
    ('Audi', 'A3', 5, 5, 350.0, 'Luxe', 80, NULL, 'Berline', 'Oui', 'automatique');

-- Insérer des voitures
INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, modele_id)
VALUES
    ('AB123CD', '2023-01-01', 10000, 'Rouge', 1),
    ('BC234DE', '2022-06-15', 15000, 'Bleu', 2),
    ('CD345EF', '2022-11-20', 20000, 'Noir', 3),
    ('DE456FG', '2023-03-10', 5000, 'Blanc', 4),
    ('EF567GH', '2022-09-05', 18000, 'Gris', 5);

-- -- Insérer des clients User
INSERT INTO User (email, motDePasse)
VALUES
    ('er', '$2a$10$OsrvL0vhN3pp0Yoqcwj7JeAI5jEv.5qMQ8eb3K2yu5KCt/C6EDlOi'), -- email et mdp = er
    ('pl', '$2a$10$1gXi.HwM1rzUxkjifXIOG.Q.pFPhlID0gHxXsPcDVFa1LtCafsfli'), -- email et mdp = pl
    ('paul', '$2a$10$TUivnPe80vY8WMRvOKOHze6Nt6xplRh/hSOcdeUKorQEvGTD/LFyS'), -- email et mdp = paul
    ('nina', '$2a$10$VO/u9KOrblF1fj2PsfOUkOmJZHtGwg7oo0KUEqDj7BBMCbsm/f9t.'), -- email et mdp = nina
    ('dupontjean@gmail.com', '$2a$10$MzdjHNpq1DT9YxeeB9emIOdZN.Vhcius6PtXtCvN6092ltEIgLIf.'), -- mdp azerty
    ('durandpierre@gmail.com', '$2a$10$MzdjHNpq1DT9YxeeB9emIOdZN.Vhcius6PtXtCvN6092ltEIgLIf.');

-- -- Insérer des  Employe
INSERT INTO Employe (id, nom, prenom, fonction)
VALUES
    (1, 'pillard', 'erwan', 'admin'),
    (2, 'thomas', 'pierre-louis', 'admin'),
    (3, 'nouille', 'paul', 'admin'),
    (4, 'nicollini', 'nina', 'admin');

-- -- Insérer des clients Client
INSERT INTO Client (id, telephone)
VALUES
    (5, '0601020304'),
    (6, '0602030405');

-- Insérer des clients particuliers
INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate)
VALUES
    (5, 'Dupont', 'Jean', '1234567890', '1990-05-10'),
    (6, 'Durand', 'Pierre', '0987654321', '1985-08-15');


-- Insérer des offres de réduction
INSERT INTO OffreReduction (nom, description, dateDebut, dateFin, pourcentageReduction, typeAdhesion)
VALUES
    ('Offre Nouveau Client', 'Réduction pour les nouveaux clients', '2024-01-01', '2024-06-30', 10.0, 'NOUVEAUCLIENT'),
    ('Offre Bronze', 'Réduction pour les clients de niveau Bronze', '2024-01-01', '2024-12-31', 5.0, 'BRONZE'),
    ('Offre Argent', 'Réduction pour les clients de niveau Argent', '2024-01-01', '2024-12-31', 10.0, 'ARGENT'),
    ('Offre Or', 'Réduction pour les clients de niveau Or', '2024-01-01', '2024-12-31', 15.0, 'OR');