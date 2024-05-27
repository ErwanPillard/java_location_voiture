# :computer: Base-projet-Java

# Diagramme de Classes

```mermaid
---
title: Diagramme de Classes
---
classDiagram
    class Reservation{
        - voiture : Voiture
        - facture : Facture
        - tarif : float
        - dateFin : LocalDateTime
        - dateDebut : LocalDateTime
        - numeroReservation : int
        - client : Client
        - etat : boolean
    
        +Reservation(voiture : Voiture, client : Client, dateFin : LocalDateTime, dateDebut : LocalDateTime, numeroReservation : int, etat : boolean)
        +calculTarif() : float
        +isEtat() : boolean
   
    }
    
    class Voiture{
        - dateMiseCirculation : LocalDate
        - immatriculation : String
        - couleur : String
        - nbKilometre : double
        - modele_id : int
        - id : int
    
        +Voiture(dateMiseCirculation : LocalDate, immatriculation : String, couleur : String, nbKilometre : double, modele_id : int)
        +Voiture(modele_id : int)
        +toArray() : Object[]
        +toString() : String
        +add(voiture : Voiture) : void
        +all() : List<Voiture>
        +allFiltredCategorie(categorie : String) : List<Voiture>
        +immatExists(immatriculation : String) : boolean
        +delete() : void
        +update(voiture : Voiture) : void
        +findByImmat(immatriculation : String) : Voiture
        +getImageByImmatriculation(immatriculation : String) : byte[]
        +addImage(immatriculation : String, imageFile : File) : void
    }
    
    class Modele{
        - id: int
        - nom: String
        - boiteVitesse : String
        - nbPlace : int
        - nbPorte : int
        - tailleCoffre : float
        - caracteristiques: String
        - prixJournalier : int
        - noteSatisfaction : int 
        - categorie : Categorie
        - attelage : boolean
    
        + Modele()
    }
    
    class Categorie{
        <<enumeration>>
        Berline
        SUV
        Familiale
        Utilitaire
        Citadine
    }
    
     class Facture{
        - numeroFacture: int
        - date : LocalDateTime
        - montant : float
        - etat: boolean

        +Facture(numeroFacture : int, date : LocalDateTime, montant : float, etat : boolean)
    }
    
    class Entreprise{
        - id: int
        - nom: String
        - numSiret: String
    
        + Entreprise(nom : String, email : String, motDePasse : String, telephone : String, numSiret : String)
        + Entreprise(nom : String, email : String, telephone : String, numSiret : String)
        + Entreprise(id : int, nom : String, numSiret : String)
        + add(entreprise : Entreprise) : void
        + toArray() : Object[]
    }
    
    class Particuler{
        - birthDate: LocalDate
        - Id : int 
    }
    
    class User{
        - nom: String
        - prenom: String
        - email : String
        - motDePasse : String
    }
    
     class Client{
        - age : int 
        - telephone : String
        - numeroAdhesion: double
        - adhesion: Boolean
        - typeAdhesion : TypeAdhesion
        
        +allUserClient() : List<User>
        +allParticuliers() : List<Particulier>
        +allEntreprises() : List<Entreprise>
        +findByTelephone(telephone : String) : Client
        +getClientById(id : int) : Client
        +emailExists(email : String) : boolean
    }
    
    class TypeAdhesion{
        <<enumeration>>
        NOUVEAUCLIENT
        BRONZE
        ARGENT
        OR
        
        - tempsAncieneteMin : int
        - tempsAncieneteMax : int
        - tauxRemise : int
        
        +TypeAdhesion(tempsAncieneteMin : int, tempsAncieneteMax : int, tauxRemise : int)
    }
    
    class OffreReduction{
    - id : int
    - nom : String
    - description : String
    - dateDebut : LocalDateTime
    - dateFin : LocalDateTime
    - pourcentageReduction : float
    - TypeAdhesion : String
    
    +OffreReduction(id : int, nom : String, description : String, dateDebut : LocalDateTime, dateFin : LocalDateTime, pourcentageReduction : float, TypeAdhesion : String)
}
    
    Reservation "0..1" --> "1" Voiture
    Reservation "1" --> "1" Facture
    Reservation "0..*" -- "1" Client
    Voiture "0..*" --> "1" Modele
    Modele -- Categorie
    TypeAdhesion -- Client
    Client --|> User
    Employe --|> User
    Entreprise --|> Client
    Particuler --|> Client




```
