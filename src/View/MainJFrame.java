package View;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {

    public static final Dimension WINDOWSIZE = new Dimension(1000,500);

    public MainJFrame() {
        super("CARECE");

        JPanel jpBody = new JPanel();
        jpBody.setLayout(new BorderLayout());

        this.setContentPane(jpBody);
    }
    public static void createAndShowGUI() {
        // Création et paramétrage de la fenêtre
        JFrame frame = new MainJFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(WINDOWSIZE);
        frame.setPreferredSize(WINDOWSIZE);

        // Centrage de la fenêtre
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);

        ClientFormView.toggle();
    }
}
