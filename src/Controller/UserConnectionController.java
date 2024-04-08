package Controller;

import View.UserConnection;
import Dao.UserConnexion;

import javax.swing.*;

public class UserConnectionController {
    private UserConnection userConnectionView;
    private UserConnexion userConnexionDao;

    public UserConnectionController(UserConnection userConnectionView, UserConnexion userConnexionDao) {
        this.userConnectionView = userConnectionView;
        this.userConnexionDao = userConnexionDao;
        initController();
    }

    private void initController() {
        userConnectionView.getLoginButton().addActionListener(e -> attemptLogin());
    }

    private void attemptLogin() {
        String username = userConnectionView.getUsername();
        String password = userConnectionView.getPassword();

        if (userConnexionDao.connect(username, password)) {
            JOptionPane.showMessageDialog(null, "Connexion réussie.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            // Mettre à jour l'état de connexion dans l'application principale si nécessaire
        } else {
            JOptionPane.showMessageDialog(null, "Échec de la connexion. Veuillez vérifier vos identifiants.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
