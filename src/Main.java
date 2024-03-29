import Dao.UserDAO;
import Dao.UserDAOImpl;
import Model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Location_Voiture";
    public static final String DATABASE_USER = "root";
    public static final String DATABASE_PASSWORD = "";


    public static void main(String[] args) throws SQLException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime.parse("20-09-2020 11:00:05", dateTimeFormatter);

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)){


            User user = new User("Pillard", "Erwan", "email", "azerty", "0782852779");

            Modele modeleGTI = new Modele(1, "Polo GTI", 5, 5, 30, "GROS CALIBRE", 90.0f, false, 4.1f, BoiteVitesse.AUTOMATIC, Categorie.BERLINE);

            Voiture voiture1 = new Voiture(LocalDateTime.parse("20-09-2020 11:00:05", dateTimeFormatter), "EV-012-EH", "GRIS", 14000, modeleGTI);

            UserDAO userDAO = new UserDAOImpl(connection);

            userDAO.add(user);

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }
}