package View.layouts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

import Controller.ModeleController;
import View.Employe.ModeleView;
import View.Employe.VoitureJTableView;
import View.MainJFrame;
import View.listeners.EventListener;
import utils.Util;

public class MenuOver extends JMenuBar {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JMenu jmFile;
    private JMenuItem jmiExit;
    private JMenuItem jmiModele;
    private JMenuItem jmiVoiture;


    private JMenu jmEdit;
    private JMenuItem jmiAdd;
    private JMenuItem jmiEdit;
    private JMenuItem jmiRemove;

    private JMenu jmHelp;
    private JMenuItem jmiAbout;

    private JPanel jpBody; // Ajoutez ce champ à votre classe MenuOver

    private EventListener eventListener;

    public MenuOver(JPanel jpBody) {
        this.jpBody = jpBody;
        configure();
        registerListeners();
    }


    private void configure() {
        jmFile = createMenu("Affichage", 'A');

        jmEdit = createMenu("Édition", 'E');

        jmHelp = createMenu("Aide", 'H');

        jmiVoiture = createMenuItem(jmFile, "Park auto", 'S', "details",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));

        jmiExit = createMenuItem(jmFile, "Quitter", 'S', "close_view",
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));


        jmiAdd = createMenuItem(jmEdit, "Ajouter Modèle", 'A', "add_obj",
                KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));


        jmiEdit = createMenuItem(jmEdit, "Éditer", 'E', "edit",
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.ALT_MASK));

        jmiRemove = createMenuItem(jmEdit, "Supprimer", 'R', "remove",
                KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));


        jmiAbout = createMenuItem(jmHelp, "À propos", 'S', "about",
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
    }


    private JMenu createMenu(String nome, char mnemonic) {
        JMenu jMenu = new JMenu(nome);
        jMenu.setMnemonic(mnemonic);
        this.add(jMenu);
        return jMenu;
    }

    private JMenuItem createMenuItem(JMenu jMenu, String name,
                                     char mnmonic, String icone, KeyStroke keyStroke){

        JMenuItem jMenuItem = new JMenuItem(name);
        jMenuItem.setAccelerator(keyStroke);
        jMenuItem.setIcon(Util.getIcon(getClass(), icone));
        jMenuItem.setMnemonic(mnmonic);
        jMenu.add(jMenuItem);

        return jMenuItem;
    }


    private void registerListeners(){
        jmiExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cmdSair();
            }
        });

        jmiVoiture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // Instancier le JScrollPane et le VoitureJTableView
                JScrollPane jspList = new JScrollPane();
                VoitureJTableView jTableList = new VoitureJTableView();
                jspList.setViewportView(jTableList);

                // Ajouter les éléments à votre interface utilisateur
                jpBody.removeAll(); // Supprimer tous les composants existants de jpBody
                jpBody.add(new Options(jTableList), BorderLayout.SOUTH);
                jpBody.add(jspList, BorderLayout.CENTER);
                jpBody.revalidate(); // Actualiser l'affichage
            }
        });

        jmiAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ModeleView.toggle();
            }
        });

        jmiEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                eventListener.cmdEdit();
            }
        });

        jmiRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                eventListener.cmdRemove();
            }
        });

        jmiAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("About");
            }
        });
    }

    public void cmdSair(){
        System.exit(0);
    }
}