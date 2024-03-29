-- Création de la table User
CREATE TABLE User
(
    id         INT AUTO_INCREMENT NOT NULL,
    nom        VARCHAR(255) NOT NULL,
    prenom     VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    motDePasse VARCHAR(255) NOT NULL,
    telephone  VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Création de la table Employe qui hérite de User
CREATE TABLE Employee
(
    id       INT          NOT NULL,
    fonction VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_employe_user FOREIGN KEY (id) REFERENCES User (id)
) ENGINE=InnoDB;

-- Création de la table Client qui hérite de User
CREATE TABLE Client
(
    id  INT NOT NULL,
    age INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_client_user FOREIGN KEY (id) REFERENCES User (id)
) ENGINE=InnoDB;

-- Création de la table Entreprise qui hérite de Client
CREATE TABLE Entreprise
(
    id          INT AUTO_INCREMENT NOT NULL,
    numeroSiret VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id),
    CONSTRAINT fk_entreprise_user FOREIGN KEY (id) REFERENCES User (id)
) ENGINE=InnoDB;

-- Création de la table Particulier qui hérite de Client
CREATE TABLE Particulier
(
    id           INT          NOT NULL,
    numeroPermis VARCHAR(255) NOT NULL UNIQUE,
    birthDate    DATE         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_particulier_user FOREIGN KEY (id) REFERENCES User (id)
) ENGINE=InnoDB;

-- Création de la table Categorie
CREATE TABLE Categorie
(
    id  INT AUTO_INCREMENT NOT NULL,
    nom VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Création de la table Modele de voiture
CREATE TABLE Modele
(
    id               INT AUTO_INCREMENT NOT NULL,
    nom              VARCHAR(255) NOT NULL,
    nbPlaces         INT          NOT NULL,
    nbPortes         INT          NOT NULL,
    tailleCoffre     FLOAT        NOT NULL,
    caracteristique  VARCHAR(255),
    prixJournalier   INT          NOT NULL,
    noteSatisfaction INT,
    categorie        ENUM('Citadine', 'Berline', 'SUV', 'Familiale', 'Utilitaire') NOT NULL,
    attelage         ENUM ('Oui', 'Non') NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Création de la table Voiture
CREATE TABLE Voiture
(
    immatriculation       VARCHAR(255) NOT NULL,
    dateMiseEnCirculation DATE         NOT NULL,
    nbKilometre           DOUBLE       NOT NULL,
    couleur               VARCHAR(255) NOT NULL,
    modele_id             INT          NOT NULL,
    PRIMARY KEY (immatriculation),
    CONSTRAINT fk_voiture_modele FOREIGN KEY (modele_id) REFERENCES Modele (id) -- Référence correcte à la clé primaire de Modele
) ENGINE=InnoDB;

-- Création de la table Facture
CREATE TABLE Facture
(
    numeroFacture INT AUTO_INCREMENT NOT NULL,
    dateEmission  DATE NOT NULL,
    montant DOUBLE NOT NULL,
    etat    ENUM ('Paye', 'Pas paye') NOT NULL,
    PRIMARY KEY (numeroFacture)
) ENGINE=InnoDB;

-- Création de la table Reservation
CREATE TABLE Reservation
(
    numReservation       INT AUTO_INCREMENT NOT NULL,
    dateDebutReservation DATE         NOT NULL,
    dateFinReservation   DATE         NOT NULL,
    tarif                FLOAT        NOT NULL,
    etat                 VARCHAR(255) NOT NULL,
    voiture_immatriculation VARCHAR(255) NOT NULL,
    facture_numeroFacture INT,
    PRIMARY KEY (numReservation),
    CONSTRAINT fk_reservation_voiture FOREIGN KEY (voiture_immatriculation) REFERENCES Voiture (immatriculation),
    CONSTRAINT fk_reservation_facture FOREIGN KEY (facture_numeroFacture) REFERENCES Facture (numeroFacture)
) ENGINE=InnoDB;
