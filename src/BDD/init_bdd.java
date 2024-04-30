package BDD;

import Dao.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Random;

public class init_bdd {

    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // Insérer 60 utilisateurs
            insertUsers(connection);

            // Insérer 40 clients (particuliers et entreprises)
            insertClient(connection);

            // Insérer les détails de 20 particuliers
            insertParticuliers(connection);

            // Insérer les détails de 20 entreprises
            insertEntreprises(connection);

            // Insérer 20 employés
            insertEmployes(connection);

            // Insérer 50 voitures
            insertVoitures(connection);

            // Insérer 10 modèles
            insertModeles(connection);

            System.out.println("La base de données a été initialisée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertUsers(Connection connection) throws SQLException {
        String userSql = "INSERT INTO User (id, email, motDePasse) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(userSql)) {
            for (int i = 1; i <= 30; i++) {
                String hashed = BCrypt.hashpw(String.valueOf(i), BCrypt.gensalt(12)); // Hashage du mot de passe
                stmt.setInt(1, i); // id
                stmt.setString(2, "" + i); // Email
                stmt.setString(3, hashed); // Mot de passe
                stmt.executeUpdate();
            }
        }
    }

    public static void insertClient(Connection connection) throws SQLException {
        String clientSql = "INSERT INTO Client (id, telephone) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(clientSql)) {
            for (int i = 1; i <= 40; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "06112233" + i); // telephone
                stmt.executeUpdate();
            }
        }
    }


    public static void insertParticuliers(Connection connection) throws SQLException {
        String particulierSql = "INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(particulierSql)) {
            for (int i = 1; i <= 20; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(3, "prenom" + i); // prenom
                stmt.setString(4, "Permis" + i); // numero de permis
                stmt.setDate(5, new java.sql.Date(new java.util.Date().getTime())); // date de naissance
                stmt.executeUpdate();
            }
        }
    }

    public static void insertEntreprises(Connection connection) throws SQLException {
        String entrepriseSql = "INSERT INTO Entreprise (id, nom, numeroSiret) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(entrepriseSql)) {
            for (int i = 21; i <= 40; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(3, "Siret" + i); // numero de siret
                stmt.executeUpdate();
            }
        }
    }

    public static void insertEmployes(Connection connection) throws SQLException {
        String employeSql = "INSERT INTO Employe (id, nom, prenom, fonction) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(employeSql)) {
            for (int i = 41; i <= 60; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(3, "prenom" + i); // prenom
                stmt.setString(4, "Fonction" + i); // fonction
                stmt.executeUpdate();
            }
        }
    }

    public static void insertVoitures(Connection connection) throws SQLException {
        String voitureSql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, modele_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(voitureSql)) {
            for (int i = 1; i <= 50; i++) {
                if (i < 10) {
                    stmt.setString(1, "AA-00" + i + "AA"); // immatriculation
                } else {
                    stmt.setString(1, "AA-0" + i + "AA"); // immatriculation
                }
                stmt.setDate(2, new java.sql.Date(new java.util.Date().getTime())); // date de mise en circulation
                stmt.setDouble(3, i); // nombre de kilomètres
                stmt.setString(4, "Couleur" + i); // couleur
                stmt.setInt(5, (i % 10) + 1); // modèle_id
                stmt.executeUpdate();
            }
        }
    }

    public static void insertModeles(Connection connection) throws SQLException {
        String modeleSql = "INSERT INTO Modele (id, marque, nom, nbPLaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage, boiteVitesse) VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Random rand = new Random();
        String[] categories = {"Citadine", "Berline", "SUV", "Familiale", "Utilitaire"};

        try (PreparedStatement stmt = connection.prepareStatement(modeleSql)) {
            for (int i = 1; i <= 10; i++) {
                int nbPlaces = 4 + rand.nextInt(2); // Aléatoire entre 4 et 5
                int nbPortes = 3 + rand.nextInt(3); // Aléatoire entre 3 et 5
                float tailleCoffre = 300.0f + rand.nextFloat() * (500.0f - 300.0f); // Aléatoire entre 300.0 et 500.0
                int prixJournalier = 25 + rand.nextInt(126); // Aléatoire entre 25 et 150
                Integer noteSatisfaction = rand.nextBoolean() ? rand.nextInt(5) + 1 : null; // Aléatoire entre 1 et 5 ou null
                String categorie = categories[rand.nextInt(categories.length)]; // Aléatoire parmi 'Citadine', 'Berline', 'SUV', 'Familiale', 'Utilitaire'
                String attelage = rand.nextBoolean() ? "Oui" : "Non"; // Aléatoire entre 'Oui' et 'Non'

                stmt.setInt(1, i); // id
                stmt.setString(2, "Marque" + i); // Marque
                stmt.setString(3, "nom" + i); // nom
                stmt.setInt(4, nbPlaces); // nombre de places
                stmt.setInt(5, nbPortes); // nombre de portes
                stmt.setFloat(6, tailleCoffre); // taille du coffre en litres
                stmt.setString(7, "Caracteristique" + i); // caractéristique
                stmt.setInt(8, prixJournalier); // prix journalier
                if (noteSatisfaction != null) {
                    stmt.setInt(9, noteSatisfaction); // note de satisfaction
                } else {
                    stmt.setNull(9, Types.INTEGER); // Si noteSatisfaction est null, on insère un NULL dans la base de données
                }
                stmt.setString(10, categorie); // catégorie
                stmt.setString(11, attelage); // attelage
                stmt.setString(12, "Automatique"); // boite de vitesse
                stmt.executeUpdate();
            }
        }
    }

    public static void insertReservation(Connection connection) throws SQLException {
        Random rand = new Random();
        String fetchClientSql = "SELECT id FROM Client ORDER BY RAND(1) LIMIT 60";

        String fetchVoituresANDModeleSql = "SELECT v.immatriculation, m.prixJournalier FROM Voiture v JOIN Modele m ON v.modele_id = m.id ORDER BY RAND() LIMIT 60";

        String reservationSql = "INSERT INTO Reservation (numReservation, dateDebutReservation, dateFinReservation, montant, etat, voiture_immatriculation, id_client) VALUES (?, ?, ?, ?, ?, ?, ?)"; // numReservation est auto-incrémenté, pas besoin de le spécifier

        int i = 1;
        try (
                Statement fetchVoituresStmt = connection.createStatement();
                ResultSet rsVoitures = fetchVoituresStmt.executeQuery(fetchVoituresANDModeleSql);
                PreparedStatement insertStmt = connection.prepareStatement(reservationSql);

                Statement fetchClientStmt = connection.createStatement();
                ResultSet rsClients = fetchClientStmt.executeQuery(fetchClientSql)
        ) {
            while (rsVoitures.next() && rsClients.next()) {
                String immatriculation = rsVoitures.getString("immatriculation");
                int prixJournalier = rsVoitures.getInt("prixJournalier");
                int idClient = rsClients.getInt("id");

                insertStmt.setInt(1, i); // numReservation
                insertStmt.setString(2, "27-04-2024"); // dateDebutReservation
                insertStmt.setString(3, "29-04-2024"); // dateFinReservation
                insertStmt.setInt(4, prixJournalier * 2); // montant
                String etat = rand.nextBoolean() ? "Confirmée" : "Non-confirmée"; // état
                insertStmt.setString(5, etat);
                insertStmt.setString(6, immatriculation); // immatriculation
                //insertStmt.setInt(7, idClient); // id_client
                insertStmt.setInt(7, i); // id_client
                i++;

                insertStmt.executeUpdate(); // Exécute la requête d'insertion
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime l'erreur SQL si elle se produit
            throw e; // Relance l'exception pour la gérer plus haut dans la pile d'appels
        }
    }


    public static void insertFacture(Connection connection) throws SQLException {
        Random rand = new Random();

        String fetchVoituresANDModeleSql = "SELECT v.immatriculation, m.prixJournalier FROM Voiture v JOIN Modele m ON v.modele_id = m.id ORDER BY RAND() LIMIT 60";

        String factureSql = "INSERT INTO Facture (numeroFacture, dateEmission, dateDebutReservation, dateFinReservation, montant, etat, " +
                "voiture_immatriculation, id_client) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int i = 1;
        try (
                Statement fetchStmt = connection.createStatement();
                ResultSet rs = fetchStmt.executeQuery(fetchVoituresANDModeleSql);
                PreparedStatement insertStmt = connection.prepareStatement(factureSql)
        ) {
            while (rs.next()) {
                String immatriculation = rs.getString("immatriculation");
                int prixJournalier = rs.getInt("prixJournalier");

                // Définir les paramètres pour chaque réservation
                insertStmt.setInt(1, i); // numeroFacture
                insertStmt.setString(2, "29-04-2024"); // dateEmission
                insertStmt.setString(3, "27-04-2024"); // dateDebutReservation
                insertStmt.setString(4, "29-04-2024"); // dateFinReservation
                insertStmt.setInt(5, prixJournalier * 2); // montant
                String etat = rand.nextBoolean() ? "Payé" : "Non-payé"; // Aléatoire entre 'Payé' et 'Non-payé'
                insertStmt.setString(6, etat); // etat
                insertStmt.setString(7, immatriculation); // immatriculation
                insertStmt.setInt(8, i); // id_client

                insertStmt.executeUpdate();
                i++;
            }
        }
    }

    public static void clearData(Connection connection) throws SQLException {
        connection.prepareStatement("DELETE FROM Particulier").executeUpdate();
        connection.prepareStatement("DELETE FROM Entreprise").executeUpdate();
        connection.prepareStatement("DELETE FROM Employe").executeUpdate();
        connection.prepareStatement("DELETE FROM Voiture").executeUpdate();
        connection.prepareStatement("DELETE FROM Modele").executeUpdate();
        connection.prepareStatement("DELETE FROM Facture").executeUpdate();
        connection.prepareStatement("DELETE FROM Reservation").executeUpdate();
        //connection.prepareStatement("DELETE FROM User").executeUpdate(); // Fais attention avec cette ligne
    }
}
