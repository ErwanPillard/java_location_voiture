package View.Employe;

import Model.OffreReduction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OffreReductionView {
    private OffreReductionJTable jTableList;
    private JButton jbAjtOffre;
    private JButton jbSuppOffre;


    public OffreReductionView(JPanel jpBody) {
        createView(jpBody);
        registerListeners();
    }

    private void registerListeners() {
        jbAjtOffre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OffreReductionForm.toggle();
            }
        });
    }

    public JPanel tabPanel(JPanel tabPanel) {

        tabPanel.setLayout(new GridBagLayout());
        //Jtable into JScroll
        jTableList = new OffreReductionJTable();
        JScrollPane jspList = new JScrollPane();

        jspList.setViewportView(jTableList);
        jspList.setPreferredSize(new Dimension(900, 700));
        jspList.setMinimumSize(new Dimension(500, 500));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1; // seul composant de sa colonne, il est donc le dernier.
        gbc.gridheight = GridBagConstraints.REMAINDER; // valeur par défaut - peut s'étendre sur une seule ligne.

        tabPanel.add(jspList, gbc);

        return tabPanel;
    }

    public JPanel leftPanel(JPanel leftPanel) {

        leftPanel.setLayout(new GridBagLayout());

        JLabel jlAjtOffre = new JLabel("Ajouter une offre de réduction");
        jbAjtOffre = new JButton("Ajouter");

        JLabel jlSuppOffre = new JLabel("Supprimer une offre de réduction");
        jbSuppOffre = new JButton("Supprimer");

        leftPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5,5,5,5);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;

        gbc.weightx = 1.;
        gbc.weighty = 1.;

        gbc.anchor = GridBagConstraints.LINE_START;

        leftPanel.add(jlAjtOffre, gbc);

        gbc.gridy++;
        leftPanel.add(jbAjtOffre, gbc);
        gbc.gridy++;
        //leftPanel.add(jlSuppOffre, gbc);
        gbc.gridy++;
        //leftPanel.add(jbSuppOffre, gbc);

        return leftPanel;
    }

    public void createView(JPanel jpBody) {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(123, 183, 191));

        JPanel tabPanel = new JPanel(new GridBagLayout()); //Panel Tableau voiture
        JPanel leftPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridheight = GridBagConstraints.REMAINDER; // valeur par défaut - peut s'étendre sur une seule ligne.
        mainPanel.add(leftPanel(leftPanel), gbc);


        gbc.gridx = 1;
        gbc.insets = new Insets(10,10,10,10);
        mainPanel.add(tabPanel(tabPanel), gbc);

        jpBody.removeAll();
        jpBody.setLayout(new BorderLayout());
        jpBody.add(mainPanel, BorderLayout.CENTER);
        jpBody.revalidate();
    }
}
