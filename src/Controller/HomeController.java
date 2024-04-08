package Controller;

import View.HomePage;
import Dao.UserConnexion;

public class HomeController {

    private HomePage homePage;
    private UserConnexion userConnexion;

    public HomeController(HomePage homePage, UserConnexion userConnexion) {
        // Log pour confirmer l'entrée dans le constructeur
        System.out.println("HomeController: Constructeur appelé");

        this.homePage = homePage;
        this.userConnexion = userConnexion;

        // Essaye d'initialiser et capture toutes les exceptions
        try {
            initController();
        } catch (Exception e) {
            e.printStackTrace();
            // Log pour une exception non prévue
            System.out.println("HomeController: Exception lors de l'initialisation");
        }
    }

    private void initController() {
        // Log pour confirmer l'initialisation du contrôleur
        System.out.println("HomeController: initialisation des contrôles");

        // Vérifie que les boutons de la HomePage existent
        if(homePage.getBtnLogin() == null) {
            // Log pour indiquer un problème potentiel
            System.out.println("HomeController: Le bouton de connexion est null");
        } else {
            // Ajoute un écouteur au bouton de connexion
            homePage.getBtnLogin().addActionListener(e -> showLoginDialog());
        }
    }

    private void showLoginDialog() {
        // Log pour confirmer que la méthode a été appelée
        System.out.println("HomeController: Affichage du dialogue de connexion");

        // Exemple fictif de récupération de nom d'utilisateur et mot de passe
        String username = "user";
        String password = "pass";

        // Appelle la méthode de connexion sur l'objet userConnexion
        boolean isConnected = userConnexion.connect(username, password);
        if (isConnected) {
            // Si la connexion réussit, tu peux mettre à jour la vue ou naviguer vers une autre page
            System.out.println("Connexion réussie");
        } else {
            // Affiche une erreur ou réessaye
            System.out.println("Échec de la connexion");
        }
    }

    public static void main(String[] args) {
        // Cet exemple suppose que tu as déjà initialisé UserConnexion et HomePage quelque part
        // UserConnexion userConnexion = new UserConnexionImpl(); // Supposons que cette classe existe et est correctement implémentée
        // HomePage homePage = new HomePage();
        // new HomeController(homePage, userConnexion);
    }
}
