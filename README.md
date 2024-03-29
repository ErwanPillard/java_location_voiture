# :computer: Base-projet-Java

# Diagramme de Classes

```mermaid
---
title: Diagramme de Classes
---
classDiagram

    Model.Reservation "0..1" --> "1" Model.Voiture
    Model.Reservation "1" --> "1" Model.Facture
    Model.Reservation "0..*" -- "1" Model.Client
    Model.Voiture "0..*" --> "1" Model.Modele
    Model.Modele -- Model.Categorie
    Model.Client --|> Model.User
    Model.Employe --|> Model.User
    Entreprise --|> Model.Client
    Particuler --|> Model.Client

    class Model.Reservation{
        - numReservation : int
        - datesDebutResa : LocalDate
        - datesfinResa : LocalDate
        - tarif : float
        - etat : String
        
        +Model.Reservation(voiture : Model.Voiture, client : Model.Client, numReservation : int, dateDebutResa : LocalDate, dateFinResa : LocalDate, tarif : float, etat : String)
    }

    class Model.Voiture{
        - immatriculation : String
        - dateMiseCirculation : LocalDate
        - nbKilometre: double
        - couleur: String

        +Model.Voiture(immatriculation : String, dateMiseCiculation : LocalDate, nbKilometre : double, couleur : String, modele : Model.Modele)

    }

    class Model.Modele{
        - id: int
        - nom: String
        - boiteVitesse : String
        - nbPlace : int
        - nbPorte : int
        - tailleCoffre : float
        - caracteristiques: String
        - prixJournalier : int
        - noteSatisfaction : int 
        - categorie : Model.Categorie
        - attelage : boolean

        + Model.Modele()
    }

class Model.Categorie{
    <<Enumeration>>
    Berline
    SUV
    Familiale
    Utilitaire
    Citadine
}

class Model.Facture{
    - numeroFacture: int
    - date : LocalDate
    - montant : float
    - etat: boolean
}

class Entreprise{
    - numSiret: int
}

class Particuler{
    - birthDate: LocalDate
    - Id : int 
}

class Model.User{
    - id: int
    - nom: String
    - prenom: String
    - email : String
    - motDePasse : String
}

class Model.Client{
    - age : int 
    - numeroAdhesion: double
    - typeAdhesion: int ou enum
    - tauxRemise: int

}

```