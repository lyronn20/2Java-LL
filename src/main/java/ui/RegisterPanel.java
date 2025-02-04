package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPanel extends JPanel {
    private MainFrame mainFrame;

    public RegisterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(5, 2));

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel pseudoLabel = new JLabel("Pseudo:");
        JTextField pseudoField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel roleLabel = new JLabel("Role (ADMIN or EMPLOYE):");
        JTextField roleField = new JTextField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String pseudo = pseudoField.getText();
                String password = new String(passwordField.getPassword());
                String role = roleField.getText().toUpperCase();
                boolean created = mainFrame.getAuthService().registerUser(email, pseudo, password, role);
                if (created) {
                    JOptionPane.showMessageDialog(mainFrame, "Registration successful.");
                    mainFrame.showPanel("Login");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Registration failed.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showPanel("Login");
            }
        });

        add(emailLabel);
        add(emailField);
        add(pseudoLabel);
        add(pseudoField);
        add(passwordLabel);
        add(passwordField);
        add(roleLabel);
        add(roleField);
        add(registerButton);
        add(backButton);
    }
}