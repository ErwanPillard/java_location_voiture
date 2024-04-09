import java.sql.SQLException;

import View.HomePage;
import Model.SessionManager;

public class Main {
    public static void main(String[] args) throws SQLException {
        SessionManager.getInstance();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomePage();
            }
        });

        /*
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)){

            User user = new User("Pillard", "Erwan", "email", "azerty");

            Client client = new Client(19, "0782852779");

            Modele modeleGTI = new Modele(1, "Polo GTI", 5, 5, 30, "GROS CALIBRE", 90.0f, false, 4.1f, BoiteVitesse.AUTOMATIC, Categorie.BERLINE);

            Voiture voiture1 = new Voiture(LocalDateTime.parse("20-09-2020", dateTimeFormatter), "EV-012-EH", "GRIS", 14000, modeleGTI);

            Reservation reservation = new Reservation(voiture1, client, LocalDateTime.parse("29-03-2024", dateTimeFormatter), LocalDateTime.parse("29-04-2024", dateTimeFormatter), 123, true);

            UserDAO userDAO = new UserDAOImpl(connection);

            userDAO.add(user);

        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
 */

        //MainJFrame.createAndShowGUI();
    }
}