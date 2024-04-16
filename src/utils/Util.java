package utils;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class Util {
    private static final String ICONS_PATH = "/View/icons/";

    public static ImageIcon getIcon(Class<?> kclass, String icone){
        return new ImageIcon(kclass.getResource(ICONS_PATH + icone + ".gif"));
    }

    public static void addFormField(JPanel panel, GridBagConstraints gbc, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    public static void clearForm(JTextComponent... jtcomponets) {
        for (JTextComponent component : jtcomponets) {
            if (component != null) {
                component.setText("");
            }
        }
    }

}
