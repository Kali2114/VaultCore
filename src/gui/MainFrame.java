package gui;

import models.*;
import service.BankService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final BankService bankService;

    private final DefaultListModel<String> usersListModel = new DefaultListModel<>();
    private final DefaultListModel<String> accountsListModel = new DefaultListModel<>();
    private final DefaultListModel<String> transactionsListModel = new DefaultListModel<>();

    private final JComboBox<User> userComboBox = new JComboBox<>();

    public MainFrame(BankService bankService) {
        this.bankService = bankService;

        setTitle("VaultCore");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Clients", createClientsPanel());
        tabs.addTab("Accounts", createAccountsPanel());
        tabs.addTab("Operations", createOperationsPanel());
        tabs.addTab("Transactions", createTransactionsPanel());

        add(tabs);

        setVisible(true);
    }

    private JPanel createClientsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel();

        JTextField firstNameField = new JTextField(12);
        JTextField lastNameField = new JTextField(12);
        JTextField emailField = new JTextField(18);
        JButton addUserButton = new JButton("Add user");

        formPanel.add(new JLabel("First name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(addUserButton);

        JList<String> usersList = new JList<>(usersListModel);

        addUserButton.addActionListener(event -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();

            if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                JOptionPane.showMessageDialog(this, "Fill all user fields.");
                return;
            }

            User user = bankService.create_user(firstName, lastName, email);
            bankService.add_user(user);

            userComboBox.addItem(user);
            usersListModel.addElement(user.get_first_name() + " " + user.get_last_name() + " | " + user.get_email());

            firstNameField.setText("");
            lastNameField.setText("");
            emailField.setText("");

            JOptionPane.showMessageDialog(this, "User added.");
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(usersList), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel();

        JComboBox<String> accountTypeBox = new JComboBox<>(new String[]{
                "Checking",
                "Saving",
                "Business"
        });

        JTextField interestRateField = new JTextField("3.0", 6);
        interestRateField.setEnabled("Saving".equals(accountTypeBox.getSelectedItem()));

        accountTypeBox.addActionListener(event -> {
            boolean isSaving = "Saving".equals(accountTypeBox.getSelectedItem());
            interestRateField.setEnabled(isSaving);
        });
        JButton createAccountButton = new JButton("Create account");

        formPanel.add(new JLabel("User:"));
        formPanel.add(userComboBox);

        formPanel.add(new JLabel("Type:"));
        formPanel.add(accountTypeBox);

        formPanel.add(new JLabel("Interest rate:"));
        formPanel.add(interestRateField);

        formPanel.add(createAccountButton);

        JList<String> accountsList = new JList<>(accountsListModel);

        createAccountButton.addActionListener(event -> {
            try {
                User user = (User) userComboBox.getSelectedItem();

                if (user == null) {
                    JOptionPane.showMessageDialog(this, "Create user first.");
                    return;
                }

                String type = (String) accountTypeBox.getSelectedItem();
                BankAccount account;

                if ("Saving".equals(type)) {
                    double interestRate = Double.parseDouble(interestRateField.getText());
                    account = bankService.create_saving_account(user, interestRate);
                } else if ("Business".equals(type)) {
                    account = bankService.create_business_account(user);
                } else {
                    account = bankService.create_checking_account(user);
                }

                bankService.add_account(account);

                accountsListModel.addElement(
                        account.get_account_number()
                                + " | "
                                + type
                                + " | owner: "
                                + user.get_first_name()
                                + " "
                                + user.get_last_name()
                                + " | balance: "
                                + account.get_balance()
                );

                JOptionPane.showMessageDialog(this, "Account created: " + account.get_account_number());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Interest rate must be a number.");
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(accountsList), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createOperationsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        JPanel depositPanel = new JPanel();
        JTextField depositAccountField = new JTextField(12);
        JTextField depositAmountField = new JTextField(8);
        JButton depositButton = new JButton("Deposit");

        depositPanel.add(new JLabel("Account:"));
        depositPanel.add(depositAccountField);
        depositPanel.add(new JLabel("Amount:"));
        depositPanel.add(depositAmountField);
        depositPanel.add(depositButton);

        JPanel withdrawPanel = new JPanel();
        JTextField withdrawAccountField = new JTextField(12);
        JTextField withdrawAmountField = new JTextField(8);
        JButton withdrawButton = new JButton("Withdraw");

        withdrawPanel.add(new JLabel("Account:"));
        withdrawPanel.add(withdrawAccountField);
        withdrawPanel.add(new JLabel("Amount:"));
        withdrawPanel.add(withdrawAmountField);
        withdrawPanel.add(withdrawButton);

        JPanel transferPanel = new JPanel();
        JTextField sourceAccountField = new JTextField(12);
        JTextField targetAccountField = new JTextField(12);
        JTextField transferAmountField = new JTextField(8);
        JButton transferButton = new JButton("Transfer");

        transferPanel.add(new JLabel("From:"));
        transferPanel.add(sourceAccountField);
        transferPanel.add(new JLabel("To:"));
        transferPanel.add(targetAccountField);
        transferPanel.add(new JLabel("Amount:"));
        transferPanel.add(transferAmountField);
        transferPanel.add(transferButton);

        JPanel monthlyPanel = new JPanel();
        JButton monthlyUpdateButton = new JButton("Apply monthly update");
        monthlyPanel.add(monthlyUpdateButton);

        depositButton.addActionListener(event -> {
            try {
                String accountNumber = depositAccountField.getText();
                double amount = Double.parseDouble(depositAmountField.getText());

                bankService.deposit_by_account_number(accountNumber, amount);

                refreshTransactions();
                refreshAccounts();

                JOptionPane.showMessageDialog(this, "Deposit completed.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        withdrawButton.addActionListener(event -> {
            try {
                String accountNumber = withdrawAccountField.getText();
                double amount = Double.parseDouble(withdrawAmountField.getText());

                bankService.withdraw_by_account_number(accountNumber, amount);

                refreshTransactions();
                refreshAccounts();

                JOptionPane.showMessageDialog(this, "Withdraw completed.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        transferButton.addActionListener(event -> {
            try {
                String sourceNumber = sourceAccountField.getText();
                String targetNumber = targetAccountField.getText();
                double amount = Double.parseDouble(transferAmountField.getText());

                bankService.transfer_by_account_number(sourceNumber, targetNumber, amount);

                refreshTransactions();
                refreshAccounts();

                JOptionPane.showMessageDialog(this, "Transfer completed.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        monthlyUpdateButton.addActionListener(event -> {
            try {
                bankService.apply_monthly_update();

                refreshTransactions();
                refreshAccounts();

                JOptionPane.showMessageDialog(this, "Monthly update applied.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        panel.add(depositPanel);
        panel.add(withdrawPanel);
        panel.add(transferPanel);
        panel.add(monthlyPanel);

        return panel;
    }

    private JPanel createTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JList<String> transactionsList = new JList<>(transactionsListModel);
        JButton refreshButton = new JButton("Refresh transactions");

        refreshButton.addActionListener(event -> refreshTransactions());

        panel.add(refreshButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(transactionsList), BorderLayout.CENTER);

        return panel;
    }

    private void refreshTransactions() {
        transactionsListModel.clear();

        for (Transaction transaction : bankService.get_transactions()) {
            transactionsListModel.addElement(transaction.toString());
        }
    }

    private void refreshAccounts() {
        accountsListModel.clear();

        for (BankAccount account : bankService.get_accounts()) {
            accountsListModel.addElement(
                    account.get_account_number()
                            + " | owner: "
                            + account.get_owner().get_first_name()
                            + " "
                            + account.get_owner().get_last_name()
                            + " | balance: "
                            + account.get_balance()
            );
        }
    }
}