-- Création de la table Model.User
CREATE TABLE User
(
    id         INT AUTO_INCREMENT NOT NULL,
    email      VARCHAR(255)       NOT NULL,
    motDePasse VARCHAR(255)       NOT NULL,
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
    nom         VARCHAR(255)       NOT NULL,
    numeroSiret VARCHAR(255)       NOT NULL UNIQUE,
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
    id               INT AUTO_INCREMENT                                             NOT NULL,
    marque           VARCHAR(255)                                                   NOT NULL,
    nom              VARCHAR(255)                                                   NOT NULL,
    nbPlaces         INT                                                            NOT NULL,
    nbPortes         INT                                                            NOT NULL,
    tailleCoffre     FLOAT                                                          NOT NULL,
    caracteristique  VARCHAR(255),
    prixJournalier   INT                                                            NOT NULL,
    noteSatisfaction INT,
    categorie        ENUM ('Citadine', 'Berline', 'SUV', 'Familiale', 'Utilitaire') NOT NULL,
    attelage         ENUM ('Oui', 'Non')                                            NOT NULL,
    boiteVitesse     ENUM ('automatique', 'manuelle')                               NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

-- Création de la table Model.Voiture
CREATE TABLE Voiture
(
    immatriculation       VARCHAR(255) NOT NULL UNIQUE,
    dateMiseEnCirculation DATE         NOT NULL,
    nbKilometre           DOUBLE       NOT NULL,
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
    dateEmission            DATE               NOT NULL,
    dateDebutReservation    DATE,
    dateFinReservation      DATE,
    montant                 FLOAT              NOT NULL,
    etat                    VARCHAR(255)       NOT NULL,
    voiture_immatriculation VARCHAR(255),
    id_client               INT                NOT NULL,
    objet                   VARCHAR(255),
    PRIMARY KEY (numeroFacture)
) ENGINE = InnoDB;

-- Création de la table Model.Reservation
CREATE TABLE Reservation
(
    numReservation          INT AUTO_INCREMENT NOT NULL,
    dateDebutReservation    DATE               NOT NULL,
    dateFinReservation      DATE               NOT NULL,
    montant                 FLOAT              NOT NULL,
    etat                    VARCHAR(255)       NOT NULL,
    voiture_immatriculation VARCHAR(255)       NOT NULL,
    id_client               INT                NOT NULL,
    PRIMARY KEY (numReservation),
    CONSTRAINT fk_reservation_voiture FOREIGN KEY (voiture_immatriculation) REFERENCES Voiture (immatriculation),
    CONSTRAINT fk_reservation_client FOREIGN KEY (id_client) REFERENCES Client (id)
) ENGINE = InnoDB;


-- Création de la table Model.OffreReduction
CREATE TABLE OffreReduction
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    nom                  VARCHAR(255)                                     NOT NULL,
    description          TEXT,
    dateDebut            DATE                                             NOT NULL,
    dateFin              DATE                                             NOT NULL,
    pourcentageReduction FLOAT                                            NOT NULL,
    typeAdhesion         ENUM ('NOUVEAUCLIENT', 'BRONZE', 'ARGENT', 'OR') NOT NULL
) ENGINE = InnoDB;

-- Création de la table ClientOffreAchat
CREATE TABLE ClientOffreAchat
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    id_client  INT NOT NULL,
    id_offre   INT NOT NULL,
    date_achat TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_client) REFERENCES Client (id),
    FOREIGN KEY (id_offre) REFERENCES OffreReduction (id)
) ENGINE = InnoDB;

-- -- Insérer des clients User
INSERT INTO User (id, email, motDePasse)
VALUES (31, 'er', '$2a$10$OsrvL0vhN3pp0Yoqcwj7JeAI5jEv.5qMQ8eb3K2yu5KCt/C6EDlOi'),   -- email et mdp = er
       (32, 'pla', '$2a$10$1gXi.HwM1rzUxkjifXIOG.Q.pFPhlID0gHxXsPcDVFa1LtCafsfli'),  -- email et mdp = pl
       (33, 'paul', '$2a$10$TUivnPe80vY8WMRvOKOHze6Nt6xplRh/hSOcdeUKorQEvGTD/LFyS'), -- email et mdp = paul
       (34, 'nina', '$2a$10$VO/u9KOrblF1fj2PsfOUkOmJZHtGwg7oo0KUEqDj7BBMCbsm/f9t.');
-- email et mdp = nina

-- -- Insérer des  Employe
INSERT INTO Employe (id, nom, prenom, fonction)
VALUES (31, 'pillard', 'erwan', 'admin'),
       (32, 'thomas', 'pierre-louis', 'admin'),
       (33, 'nouille', 'paul', 'admin'),
       (34, 'nicollini', 'nina', 'admin');

-- Insérer des offres de réduction
INSERT INTO OffreReduction (nom, description, dateDebut, dateFin, pourcentageReduction, typeAdhesion)
VALUES ('Offre Nouveau Client', 'Réduction pour les nouveaux clients', '2024-01-01', '2024-06-30', 10.0,
        'NOUVEAUCLIENT'),
       ('Offre Bronze', 'Réduction pour les clients de niveau Bronze', '2024-01-01', '2024-12-31', 5.0, 'BRONZE'),
       ('Offre Argent', 'Réduction pour les clients de niveau Argent', '2024-01-01', '2024-12-31', 10.0, 'ARGENT'),
       ('Offre Or', 'Réduction pour les clients de niveau Or', '2024-01-01', '2024-12-31', 15.0, 'OR');