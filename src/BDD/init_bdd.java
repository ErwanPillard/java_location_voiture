package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class init_bdd {
    // Informations de connexion à la base de données
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Location_Voiture";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    public static void main(String[] args) {
        // Charge le driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Établit la connexion avec la base de données
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {

            // Insère des données dans la table 'Model.User'
            try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO User (nom, prenom, email, motDePasse, telephone) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setString(1, "Doe");
                stmt.setString(2, "John");
                stmt.setString(3, "john.doe@example.com");
                stmt.setString(4, "123");
                stmt.setString(5, "0606060606");
                stmt.executeUpdate();
            }

            System.out.println("La base de données a été initialisée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
