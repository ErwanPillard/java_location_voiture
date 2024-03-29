package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class init_bdd {

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Location_Voiture";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
                // Optionnel : Nettoyer la base de données si nécessaire
                //clearData(connection);

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

                System.out.println("La base de données a été initialisée avec succès !");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void insertUsers(Connection connection) throws SQLException {
        String userSql = "INSERT INTO User (nom, prenom, email, motDePasse, telephone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(userSql)) {
            for (int i = 1; i <= 60; i++) {
                stmt.setString(1, "Nom" + i); // Nom
                stmt.setString(2, "Prenom" + i); // Prénom
                stmt.setString(3, "email" + i); // Email
                stmt.setString(4, "0" + i); // Mot de passe
                stmt.setString(5, "0606060" + String.format("%03d", i)); // Téléphone
                stmt.executeUpdate();
            }
        }
    }

    public static void insertClient(Connection connection) throws SQLException {
        String clientSql = "INSERT INTO Client (id, age) VALUES (?, ?)";
        Random rand = new Random(); // generer un nombre aléatoire

        try (PreparedStatement stmt = connection.prepareStatement(clientSql)) {
            for (int i = 1; i <= 40; i++) {
                int randomAge = 18 + rand.nextInt(52); // Génère un âge aléatoire entre 18 et 70.

                stmt.setInt(1, i); // id
                stmt.setInt(2, randomAge); // age

                stmt.executeUpdate();
            }
        }
    }


    private static void insertParticuliers(Connection connection) throws SQLException {
        String particulierSql = "INSERT INTO Particulier (id, numeroPermis, birthDate) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(particulierSql)) {
            for (int i = 1; i <= 20; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "Permis" + i); // numero de permis
                stmt.setDate(3, new java.sql.Date(new java.util.Date().getTime())); // date de naissance
                stmt.executeUpdate();
            }
        }
    }

    private static void insertEntreprises(Connection connection) throws SQLException {
        String entrepriseSql = "INSERT INTO Entreprise (id, numeroSiret) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(entrepriseSql)) {
            for (int i = 21; i <= 40; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "Siret" + i); // numero de siret
                stmt.executeUpdate();
            }
        }
    }

    public static void insertEmployes(Connection connection) throws SQLException {
        String employeSql = "INSERT INTO Employe (id, fonction) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(employeSql)) {
            for (int i = 41; i <= 60; i++) {
                stmt.setInt(1, i); // id
                stmt.setString(2, "Fonction" + i); // fonction
                stmt.executeUpdate();
            }
        }
    }

    private static void insertVoitures(Connection connection) throws SQLException {
        String voitureSql = "INSERT INTO Voiture (immatriculation, dateMiseEnCirculation, nbKilometre, couleur, modele_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(voitureSql)) {
            for (int i = 1; i <= 50; i++) {
                stmt.setString(1, "Immat" + i); // immatriculation
                stmt.setDate(2, new java.sql.Date(new java.util.Date().getTime())); // date de mise en circulation
                stmt.setDouble(3, i * 1000.0); // nombre de kilomètres
                stmt.setString(4, "Couleur" + i); // couleur
                stmt.setInt(5, (i % 10) + 1); // modèle
                stmt.executeUpdate();
            }
        }
    }

    private static void insertModeles(Connection connection) throws SQLException {
        String modeleSql = "INSERT INTO Modele (nom, nbPlaces, nbPortes, tailleCoffre, caracteristique, prixJournalier, noteSatisfaction, categorie, attelage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(modeleSql)) {
            for (int i = 1; i <= 10; i++) {
                stmt.setString(1, "Modele" + i); // nom
                stmt.setInt(2, 4); // nombre de places
                stmt.setInt(3, 4); // nombre de portes
                stmt.setFloat(4, 300.0f); // taille du coffre en litres
                stmt.setString(5, "Caracteristique" + i);
                stmt.setInt(6, 50 * i); // prix journalier
                stmt.setInt(7, (i % 5) + 1); // note de satisfaction
                stmt.setString(8, "Citadine"); // catégorie
                stmt.setString(9, "Non"); // attelage
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
