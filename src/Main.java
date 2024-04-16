import Model.SessionManager;
import View.HomePage;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        SessionManager.getInstance();

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomePage();
            }
        });
    }
}