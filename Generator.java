import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Generator {
    Alphabet alphabet;

    public Generator(boolean IncludeUpper, boolean IncludeLower, boolean IncludeNum, boolean IncludeSym) {
        alphabet = new Alphabet(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
    }

    public void mainLoop() {
        JFrame frame = new JFrame("Password Generator Using Java AWT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JButton generateButton = new JButton("Password Generator");
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                requestPassword();
            }
        });
        panel.add(generateButton);

        JButton checkButton = new JButton("Password Strength Check");
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkPassword();
            }
        });
        panel.add(checkButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private Password generatePassword(int length) {
        final StringBuilder pass = new StringBuilder("");

        final int alphabetLength = alphabet.getAlphabet().length();

        int max = alphabetLength - 1;
        int min = 0;
        int range = max - min + 1;

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * range) + min;
            pass.append(alphabet.getAlphabet().charAt(index));
        }

        return new Password(pass.toString());
    }

    private void showMessageDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

    private void requestPassword() {
        String title = "Password Generator";
        int result = JOptionPane.showConfirmDialog(null,
                "Do you want to include lowercase letters in your password?",
                title, JOptionPane.YES_NO_OPTION);
        boolean includeLower = result == JOptionPane.YES_OPTION;

        result = JOptionPane.showConfirmDialog(null,
                "Do you want to include uppercase letters in your password?",
                title, JOptionPane.YES_NO_OPTION);
        boolean includeUpper = result == JOptionPane.YES_OPTION;

        result = JOptionPane.showConfirmDialog(null,
                "Do you want to include numbers in your password?",
                title, JOptionPane.YES_NO_OPTION);
        boolean includeNum = result == JOptionPane.YES_OPTION;

        result = JOptionPane.showConfirmDialog(null,
                "Do you want to include symbols in your password?",
                title, JOptionPane.YES_NO_OPTION);
        boolean includeSym = result == JOptionPane.YES_OPTION;

        if (!includeLower && !includeUpper && !includeNum && !includeSym) {
            showMessageDialog("You have selected no characters to generate your password.\n" +
                    "At least one character type should be selected.", title, JOptionPane.WARNING_MESSAGE);
            return;
        }

        String lengthInput = JOptionPane.showInputDialog(null,
                "Enter the length of the password:", title, JOptionPane.PLAIN_MESSAGE);

        if (lengthInput == null) {
            return; // User clicked cancel
        }

        int length;
        try {
            length = Integer.parseInt(lengthInput);
        } catch (NumberFormatException e) {
            showMessageDialog("Invalid input. Please enter a valid integer for the length.", title, JOptionPane.ERROR_MESSAGE);
            return;
        }

        final Generator generator = new Generator(includeUpper, includeLower, includeNum, includeSym);
        final Password password = generator.generatePassword(length);

        showMessageDialog("Your generated password: " + password, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkPassword() {
        String input = JOptionPane.showInputDialog(null,
                "Enter your password:", "Check Your Password Strength", JOptionPane.PLAIN_MESSAGE);

        if (input == null) {
            return;
        }

        final Password p = new Password(input);
        showMessageDialog("Password strength: " + p.calculateScore(), "Check Your Password Strength", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Generator(true, true, true, true).mainLoop();
            }
        });
    }
}
