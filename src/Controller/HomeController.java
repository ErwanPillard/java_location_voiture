package Controller;

import Dao.UserConnection;
import Model.SessionManager;
import Model.User;
import View.HomePage;

import java.sql.SQLException;

public class HomeController {
    private final HomePage homePage;
    private final UserConnection userConnexion;


    public HomeController(HomePage homePage, UserConnection userConnexion) {
        this.homePage = homePage;
        this.userConnexion = userConnexion;

        // Essaye d'initialiser et capture toutes les exceptions
        try {
            initController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initController() {
        homePage.getBtnLogin().addActionListener(e -> {
            try {
                showLoginDialog();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void showLoginDialog() throws SQLException {
        // Exemple fictif de récupération de nom d'utilisateur et mot de passe
        String username = "user";
        String password = "pass";

        // Appelle la méthode de connexion sur l'objet userConnexion
        User user = userConnexion.connect(username, password);
        if (user != null) {
            SessionManager.getInstance().logIn(user);
        } else {
            SessionManager.getInstance().logOut();
        }
    }
}
