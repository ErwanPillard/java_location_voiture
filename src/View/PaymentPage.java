package View;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Random;

public class PaymentPage extends JFrame {
    private JTextField cardNumberField;
    private JPasswordField cvvField;
    private JTextField expirationDateField;
    private JTextField cardHolderField;
    private JButton submitButton;

    public PaymentPage() {
        setTitle("Page de Paiement");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel cardNumberLabel = new JLabel("Numéro de carte bancaire:");
        cardNumberField = new JTextField();
        ((AbstractDocument) cardNumberField.getDocument()).setDocumentFilter(new CardNumberFilter());

        JLabel expirationDateLabel = new JLabel("Date d'expiration (MM/AA):");
        expirationDateField = new JTextField();

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField();
        cvvField.setDocument(new JTextFieldLimit(3));

        JLabel cardHolderLabel = new JLabel("Nom du titulaire:");
        cardHolderField = new JTextField();

        submitButton = new JButton("Soumettre");

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(expirationDateLabel);
        panel.add(expirationDateField);
        panel.add(cvvLabel);
        panel.add(cvvField);
        panel.add(cardHolderLabel);
        panel.add(cardHolderField);
        panel.add(new JLabel());
        panel.add(submitButton);

        add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    showLoadingScreen();
                }
            }
        });
    }

    private boolean validateFields() {
        String cardNumber = cardNumberField.getText().replaceAll(" ", "");
        String cvv = new String(cvvField.getPassword());
        String expirationDate = expirationDateField.getText();
        String cardHolder = cardHolderField.getText();

        if (cardNumber.length() != 16) {
            JOptionPane.showMessageDialog(this, "Le numéro de carte bancaire doit contenir 16 chiffres.");
            return false;
        }

        if (cvv.length() != 3) {
            JOptionPane.showMessageDialog(this, "Le CVV doit contenir 3 chiffres.");
            return false;
        }

        if (!expirationDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "La date d'expiration doit être au format MM/AA.");
            return false;
        }

        if (!isValidExpirationDate(expirationDate)) {
            JOptionPane.showMessageDialog(this, "La date d'expiration doit être valide et pas antérieure à la date actuelle.");
            return false;
        }

        if (cardHolder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom du titulaire ne peut pas être vide.");
            return false;
        }

        return true;
    }

    private boolean isValidExpirationDate(String expirationDate) {
        try {
            YearMonth expDate = YearMonth.parse(expirationDate, java.time.format.DateTimeFormatter.ofPattern("MM/yy"));
            YearMonth currentDate = YearMonth.now();
            return expDate.isAfter(currentDate) || expDate.equals(currentDate);
        } catch (Exception e) {
            return false;
        }
    }

    private void showLoadingScreen() {
        JDialog loadingDialog = new JDialog(this, "Traitement du paiement...", true);
        JPanel panel= new JPanel(new BorderLayout());
        JLabel loadingLabel = new JLabel("Veuillez patienter...", SwingConstants.CENTER);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);


        JLabel loadingIcon = new JLabel(new ImageIcon(new ImageIcon("loaddin.gif").getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT)));
        //panel.add(loadingIcon, BorderLayout.CENTER);
        panel.add(loadingLabel, BorderLayout.CENTER);

        panel.add(progressBar, BorderLayout.SOUTH);

        loadingDialog.add(panel);
        //loadingDialog.add(loadingLabel);
        loadingDialog.setSize(300, 100);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setUndecorated(true);

        // Show the loading dialog
        SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));

        // Simulate a loading time between 3 and 4 seconds
        Random random = new Random();
        int loadingTime = 3000 + random.nextInt(1000); // random time between 3000 and 4000 milliseconds

        Timer timer = new Timer(loadingTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadingDialog.dispose();
                JOptionPane.showMessageDialog(PaymentPage.this, "Paiement effectué avec succès !");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    private static class CardNumberFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (char c : string.toCharArray()) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            String newString = sb.toString();
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            StringBuilder updatedText = new StringBuilder(currentText).insert(offset, newString);
            reformatText(fb, updatedText);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (char c : string.toCharArray()) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                }
            }
            String newString = sb.toString();
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            StringBuilder updatedText = new StringBuilder(currentText).replace(offset, offset + length, newString);
            reformatText(fb, updatedText);
        }

        private void reformatText(FilterBypass fb, StringBuilder text) throws BadLocationException {
            // Remove existing spaces
            for (int i = text.length() - 1; i >= 0; i--) {
                if (text.charAt(i) == ' ') {
                    text.deleteCharAt(i);
                }
            }

            // Add spaces every 4 digits
            for (int i = 4; i < text.length(); i += 5) {
                text.insert(i, ' ');
            }

            // Replace the entire text with the new formatted text
            fb.replace(0, fb.getDocument().getLength(), text.toString(), null);
        }
    }

    private static class JTextFieldLimit extends PlainDocument {
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentPage().setVisible(true));
    }
}
