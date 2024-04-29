package Controller;

import Dao.UserConnection;
import Model.SessionManager;
import Model.User;
import View.ConnexionUtilisateur;
import View.HomePage;

import javax.swing.*;
import java.sql.SQLException;

public class UserConnectionController {
    private final ConnexionUtilisateur connexionUtilisateurView;
    private final UserConnection userConnectionDao;
    private final HomePage homePage;
    private JDialog loginDialog;

    public UserConnectionController(HomePage homePage, ConnexionUtilisateur connexionUtilisateurView, UserConnection userConnectionDao) {
        this.homePage = homePage;
        this.connexionUtilisateurView = connexionUtilisateurView;
        this.userConnectionDao = userConnectionDao;
        initController();
    }

    public void showLoginDialog(JFrame parentFrame) {
        loginDialog = new JDialog(parentFrame, "Connexion", true);
        loginDialog.setContentPane(connexionUtilisateurView);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(parentFrame);
        loginDialog.setVisible(true);
    }

    private void initController() {
        connexionUtilisateurView.getLoginButton().addActionListener(e -> {
            try {
                attemptLogin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void attemptLogin() throws SQLException {
        String email = connexionUtilisateurView.getUsername(); // Assure-toi que cette méthode renvoie bien le courriel de l'utilisateur.
        String password = connexionUtilisateurView.getPassword();

        User user = userConnectionDao.connect(email, password);
        if (user != null) {
            SessionManager.getInstance().logIn(user);
            loginDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Échec de la connexion. Veuillez vérifier vos identifiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
