package utils;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class util {

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
