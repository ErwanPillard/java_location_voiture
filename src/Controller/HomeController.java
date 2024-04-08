package Controller;

import View.ClientFormView;
import View.HomePage;
import Dao.UserConnexion;

public class HomeController {

    private HomePage homePage;
    private UserConnexion userConnexion;


    public HomeController(HomePage homePage, UserConnexion userConnexion) {
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
        boolean isConnected = userConnexion.connect(username, password);
        if (isConnected) {
            homePage.setUserLoggedIn(true);
        } else {
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
