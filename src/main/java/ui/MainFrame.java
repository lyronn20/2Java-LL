package ui;

import auth.AuthService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AuthService authService;

    public MainFrame() {
        authService = new AuthService();
        setTitle("Application de Gestion");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new RegisterPanel(this), "Register");
        mainPanel.add(new AdminPanel(this), "Admin");
        mainPanel.add(new EmployeePanel(this), "Employee");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public AuthService getAuthService() {
        return authService;
    }
}