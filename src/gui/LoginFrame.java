package gui;

import service.BankService;
import storage.FileStorage;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private final BankService bank_service;
    private final FileStorage storage;

    public LoginFrame(BankService bank_service, FileStorage storage) {
        this.bank_service = bank_service;
        this.storage = storage;

        setTitle("VaultCore - Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField login_field = new JTextField();
        JPasswordField password_field = new JPasswordField();

        JButton login_button = new JButton("Login");

        panel.add(new JLabel("Login:"));
        panel.add(login_field);
        panel.add(new JLabel("Password:"));
        panel.add(password_field);
        panel.add(new JLabel(""));
        panel.add(login_button);

        add(panel);

        login_button.addActionListener(event -> {
            String login = login_field.getText();
            String password = new String(password_field.getPassword());

            if (ADMIN_LOGIN.equals(login) && ADMIN_PASSWORD.equals(password)) {
                new MainFrame(bank_service, storage);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login or password.");
                password_field.setText("");
            }
        });

        setVisible(true);
    }
}