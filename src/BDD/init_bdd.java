package BDD;

import Dao.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Random;

public class init_bdd {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void insertUsers(Connection connection) throws SQLException {
        String userSql = "INSERT INTO User (id, email, motDePasse) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(userSql)) {
            for (int i = 1; i <= 60; i++) {
                stmt.setString(2, "" + i); // Email
                stmt.setString(3, "" + i); // Mot de passe
                stmt.executeUpdate();
            }
        }
    }

    public static void insertClient(Connection connection) throws SQLException {
        String clientSql = "INSERT INTO Client (id, telephone) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(clientSql)) {
            for (int i = 1; i <= 40; i++) {
                stmt.setString(2, "06112233" + i); // telephone
                stmt.executeUpdate();
            }
        }
    }


    public static void insertParticuliers(Connection connection) throws SQLException {
        String particulierSql = "INSERT INTO Particulier (id, nom, prenom, numeroPermis, birthDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(particulierSql)) {
            for (int i = 1; i <= 20; i++) {
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
                stmt.setString(2, "nom" + i); // nom
                stmt.setString(2, "Siret" + i); // numero de siret
                stmt.executeUpdate();
            }
        }
    }

    public static void insertEmployes(Connection connection) throws SQLException {
        String employeSql = "INSERT INTO Employe (id, nom, prenom, fonction) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(employeSql)) {
            for (int i = 41; i <= 60; i++) {
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
                stmt.setString(1, "Immat" + i); // immatriculation
                stmt.setDate(2, new java.sql.Date(new java.util.Date().getTime())); // date de mise en circulation
                stmt.setDouble(3, i); // nombre de kilomètres
                stmt.setString(4, "Couleur" + i); // couleur
                stmt.setInt(5, (i % 10) + 1); // modèle_id
                stmt.executeUpdate();
            }
        }
    }

    public static void insertModeles(Connection connection) throws SQLException {
        String modeleSql = "INSERT INTO Modele (nom, nbPlaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

                stmt.setString(1, "Modele" + i); // nom
                stmt.setInt(2, nbPlaces); // nombre de places
                stmt.setInt(3, nbPortes); // nombre de portes
                stmt.setFloat(4, tailleCoffre); // taille du coffre en litres
                stmt.setString(5, "Caracteristique" + i);
                stmt.setInt(6, prixJournalier); // prix journalier
                if (noteSatisfaction != null) {
                    stmt.setInt(7, noteSatisfaction); // note de satisfaction
                } else {
                    stmt.setNull(7, Types.INTEGER); // Si noteSatisfaction est null, on insère un NULL dans la base de données
                }
                stmt.setString(8, categorie); // catégorie
                stmt.setString(9, attelage); // attelage
                stmt.executeUpdate();
            }
        }
    }

    public static void clearData(Connection connection) throws SQLException {
        connection.prepareStatement("DELETE FROM Particulier").executeUpdate();
        connection.prepareStatement("DELETE FROM Entreprise").executeUpdate();
        connection.prepareStatement("DELETE FROM Employe").executeUpdate();
        connection.prepareStatement("DELETE FROM Voiture").executeUpdate();
        connection.prepareStatement("DELETE FROM Modele").executeUpdate();
        //connection.prepareStatement("DELETE FROM User").executeUpdate(); // Fais attention avec cette ligne
    }
}
