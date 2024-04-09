package Controller;

import Model.SessionManager;
import Model.User;
import View.HomePage;
import Dao.UserConnection;

public class HomeController {
    private HomePage homePage;
    private UserConnection userConnexion;


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
        homePage.getBtnLogin().addActionListener(e -> showLoginDialog());
    }

    private void showLoginDialog() {
        // Exemple fictif de récupération de nom d'utilisateur et mot de passe
        String username = "user";
        String password = "pass";

        // Appelle la méthode de connexion sur l'objet userConnexion
        User user = userConnexion.connect(username, password);
        if (user != null) {
            SessionManager.getInstance().logIn(user);
            homePage.setUserLoggedIn(true);
        } else {
            SessionManager.getInstance().logOut();
            homePage.setUserLoggedIn(false);
        }
    }

    public static void main(String[] args) {
        // Cet exemple suppose que tu as déjà initialisé UserConnexion et HomePage quelque part
        // UserConnexion userConnexion = new UserConnexionImpl(); // Supposons que cette classe existe et est correctement implémentée
        // HomePage homePage = new HomePage();
        // new HomeController(homePage, userConnexion);
    }
}
