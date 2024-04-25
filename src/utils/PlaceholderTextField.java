package utils;

import javax.swing.*;
import java.awt.*;

public class PlaceholderTextField extends JTextField {

    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && ! (FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            int height = getHeight();
            FontMetrics fm = g.getFontMetrics();
            Insets insets = getInsets();
            int width = getWidth() - (insets.left + insets.right);
            int x = insets.left;
            int y = height / 2 - fm.getHeight() / 2 + fm.getAscent();
            g.setColor(UIManager.getColor("textInactiveText"));
            g.drawString(placeholder, x, y);
        }
    }
}
