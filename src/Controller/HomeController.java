package Controller;

import View.HomePage;
import Model.User;
import Dao.UserConnexion;

public class HomeController {

    private HomePage homePage;
    private UserConnexion userConnexion;

    public HomeController(HomePage homePage, UserConnexion userConnexion) {
        this.homePage = homePage;
        this.userConnexion = userConnexion;
        initController();
    }

    private void initController() {
        homePage.getBtnLogin().addActionListener(e -> login());
    }

    private void login() {
        // Ici, tu récupères les données nécessaires pour la connexion (par exemple, depuis un dialogue de connexion)
        // Puis tu utilises userConnexion pour vérifier ces informations
        // Enfin, tu mets à jour homePage selon le résultat de cette connexion
    }
}
