package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class init_bdd {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Location_Voiture";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            // Insérer 60 utilisateurs
            insertUsers(connection);
            // Insérer 20 particuliers
            insertParticuliers(connection);
            // Insérer 20 entreprises
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

    private static void insertUsers(Connection connection) throws SQLException {
        String userSql = "INSERT INTO User (nom, prenom, email, motDePasse, telephone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(userSql)) {
            for (int i = 1; i <= 60; i++) {
                stmt.setString(1, "Nom" + i);
                stmt.setString(2, "Prenom" + i);
                stmt.setString(3, "user" + i + "@example.com");
                stmt.setString(4, "password" + i);
                stmt.setString(5, "0606060" + String.format("%03d", i));
                stmt.executeUpdate();
            }
        }
    }

    private static void insertParticuliers(Connection connection) throws SQLException {
        // Ajoute ton code ici pour insérer 20 particuliers en utilisant une boucle et un PreparedStatement.
    }

    private static void insertEntreprises(Connection connection) throws SQLException {
        // Ajoute ton code ici pour insérer 20 entreprises.
    }

    private static void insertEmployes(Connection connection) throws SQLException {
        // Ajoute ton code ici pour insérer 20 employés.
    }

    private static void insertVoitures(Connection connection) throws SQLException {
        // Ajoute ton code ici pour insérer 50 voitures.
    }

    private static void insertModeles(Connection connection) throws SQLException {
        // Ajoute ton code ici pour insérer 10 modèles.
    }
}
