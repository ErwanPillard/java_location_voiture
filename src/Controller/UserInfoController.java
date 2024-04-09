package Controller;

import Model.SessionManager;
import View.HomePage;
import View.UserInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInfoController {
    private final UserInfo userInfoView;

    public UserInfoController(UserInfo userInfoView) {
        this.userInfoView = userInfoView;
        initController();
    }

    private void initController() {
        // Ajoute un ActionListener au bouton de déconnexion
        userInfoView.getBtnLogout().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void logout() {
        SessionManager.getInstance().logOut(); // Déconnecte l'utilisateur
        userInfoView.dispose(); // Ferme la fenêtre UserInfo

        // Rouvre la page d'accueil
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }
}
