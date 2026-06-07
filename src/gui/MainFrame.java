package gui;

import models.*;
import service.BankService;
import storage.FileStorage;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class MainFrame extends JFrame {
    private final BankService bank_service;
    private final FileStorage storage;

    private final DefaultListModel<String> users_list_model = new DefaultListModel<>();
    private final DefaultListModel<String> accounts_list_model = new DefaultListModel<>();
    private final DefaultListModel<String> transactions_list_model = new DefaultListModel<>();

    private final JComboBox<User> user_combo_box = new JComboBox<>();

    public MainFrame(BankService bank_service, FileStorage storage) {
        this.bank_service = bank_service;
        this.storage = storage;

        setTitle("VaultCore");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Clients", create_clients_panel());
        tabs.addTab("Accounts", create_accounts_panel());
        tabs.addTab("Operations", create_operations_panel());
        tabs.addTab("Transactions", create_transactions_panel());

        add(tabs);

        refresh_users();
        refresh_accounts();
        refresh_transactions();

        setVisible(true);
    }

    private JPanel create_clients_panel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form_panel = new JPanel();

        JTextField first_name_field = new JTextField(12);
        JTextField last_name_field = new JTextField(12);
        JTextField email_field = new JTextField(18);
        JButton add_user_button = new JButton("Add user");

        form_panel.add(new JLabel("First name:"));
        form_panel.add(first_name_field);
        form_panel.add(new JLabel("Last name:"));
        form_panel.add(last_name_field);
        form_panel.add(new JLabel("Email:"));
        form_panel.add(email_field);
        form_panel.add(add_user_button);

        JList<String> users_list = new JList<>(users_list_model);

        add_user_button.addActionListener(event -> {
            try {
                String first_name = first_name_field.getText();
                String last_name = last_name_field.getText();
                String email = email_field.getText();

                if (first_name.isBlank() || last_name.isBlank() || email.isBlank()) {
                    JOptionPane.showMessageDialog(this, "Fill all user fields.");
                    return;
                }

                User user = bank_service.create_user(first_name, last_name, email);
                bank_service.add_user(user);

                refresh_users();
                save_data();

                first_name_field.setText("");
                last_name_field.setText("");
                email_field.setText("");

                JOptionPane.showMessageDialog(this, "User added.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        panel.add(form_panel, BorderLayout.NORTH);
        panel.add(new JScrollPane(users_list), BorderLayout.CENTER);

        return panel;
    }

    private JPanel create_accounts_panel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form_panel = new JPanel();

        JComboBox<String> account_type_box = new JComboBox<>(new String[]{
                "Checking",
                "Saving",
                "Business"
        });

        JTextField interest_rate_field = new JTextField("3.0", 6);
        interest_rate_field.setEnabled("Saving".equals(account_type_box.getSelectedItem()));

        account_type_box.addActionListener(event -> {
            boolean is_saving = "Saving".equals(account_type_box.getSelectedItem());
            interest_rate_field.setEnabled(is_saving);
        });

        JButton create_account_button = new JButton("Create account");

        form_panel.add(new JLabel("User:"));
        form_panel.add(user_combo_box);
        form_panel.add(new JLabel("Type:"));
        form_panel.add(account_type_box);
        form_panel.add(new JLabel("Interest rate:"));
        form_panel.add(interest_rate_field);
        form_panel.add(create_account_button);

        JList<String> accounts_list = new JList<>(accounts_list_model);

        create_account_button.addActionListener(event -> {
            try {
                User user = (User) user_combo_box.getSelectedItem();

                if (user == null) {
                    JOptionPane.showMessageDialog(this, "Create user first.");
                    return;
                }

                String type = (String) account_type_box.getSelectedItem();
                BankAccount account;

                if ("Saving".equals(type)) {
                    BigDecimal interest_rate = new BigDecimal(interest_rate_field.getText());
                    account = bank_service.create_saving_account(user, interest_rate);
                } else if ("Business".equals(type)) {
                    account = bank_service.create_business_account(user);
                } else {
                    account = bank_service.create_checking_account(user);
                }

                bank_service.add_account(account);

                refresh_accounts();
                save_data();

                JOptionPane.showMessageDialog(this, "Account created: " + account.get_account_number());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Interest rate must be a number.");
            }
        });

        panel.add(form_panel, BorderLayout.NORTH);
        panel.add(new JScrollPane(accounts_list), BorderLayout.CENTER);

        return panel;
    }

    private JPanel create_operations_panel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        JPanel deposit_panel = new JPanel();
        JTextField deposit_account_field = new JTextField(12);
        JTextField deposit_amount_field = new JTextField(8);
        JButton deposit_button = new JButton("Deposit");

        deposit_panel.add(new JLabel("Account:"));
        deposit_panel.add(deposit_account_field);
        deposit_panel.add(new JLabel("Amount:"));
        deposit_panel.add(deposit_amount_field);
        deposit_panel.add(deposit_button);

        JPanel withdraw_panel = new JPanel();
        JTextField withdraw_account_field = new JTextField(12);
        JTextField withdraw_amount_field = new JTextField(8);
        JButton withdraw_button = new JButton("Withdraw");

        withdraw_panel.add(new JLabel("Account:"));
        withdraw_panel.add(withdraw_account_field);
        withdraw_panel.add(new JLabel("Amount:"));
        withdraw_panel.add(withdraw_amount_field);
        withdraw_panel.add(withdraw_button);

        JPanel transfer_panel = new JPanel();
        JTextField source_account_field = new JTextField(12);
        JTextField target_account_field = new JTextField(12);
        JTextField transfer_amount_field = new JTextField(8);
        JButton transfer_button = new JButton("Transfer");

        transfer_panel.add(new JLabel("From:"));
        transfer_panel.add(source_account_field);
        transfer_panel.add(new JLabel("To:"));
        transfer_panel.add(target_account_field);
        transfer_panel.add(new JLabel("Amount:"));
        transfer_panel.add(transfer_amount_field);
        transfer_panel.add(transfer_button);

        JPanel monthly_panel = new JPanel();
        JButton monthly_update_button = new JButton("Apply monthly update");
        monthly_panel.add(monthly_update_button);

        deposit_button.addActionListener(event -> {
            try {
                String account_number = deposit_account_field.getText();
                BigDecimal amount = new BigDecimal(deposit_amount_field.getText());

                bank_service.deposit_by_account_number(account_number, amount);

                refresh_accounts();
                refresh_transactions();
                save_data();

                JOptionPane.showMessageDialog(this, "Deposit completed.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Amount must be a number.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        withdraw_button.addActionListener(event -> {
            try {
                String account_number = withdraw_account_field.getText();
                BigDecimal amount = new BigDecimal(withdraw_amount_field.getText());

                bank_service.withdraw_by_account_number(account_number, amount);

                refresh_accounts();
                refresh_transactions();
                save_data();

                JOptionPane.showMessageDialog(this, "Withdraw completed.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Amount must be a number.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        transfer_button.addActionListener(event -> {
            try {
                String source_number = source_account_field.getText();
                String target_number = target_account_field.getText();
                BigDecimal amount = new BigDecimal(transfer_amount_field.getText());

                bank_service.transfer_by_account_number(source_number, target_number, amount);

                refresh_accounts();
                refresh_transactions();
                save_data();

                JOptionPane.showMessageDialog(this, "Transfer completed.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Amount must be a number.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        monthly_update_button.addActionListener(event -> {
            try {
                bank_service.apply_monthly_update();

                refresh_accounts();
                refresh_transactions();
                save_data();

                JOptionPane.showMessageDialog(this, "Monthly update applied.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        });

        panel.add(deposit_panel);
        panel.add(withdraw_panel);
        panel.add(transfer_panel);
        panel.add(monthly_panel);

        return panel;
    }

    private JPanel create_transactions_panel() {
        JPanel panel = new JPanel(new BorderLayout());

        JList<String> transactions_list = new JList<>(transactions_list_model);
        JButton refresh_button = new JButton("Refresh transactions");

        refresh_button.addActionListener(event -> refresh_transactions());

        panel.add(refresh_button, BorderLayout.NORTH);
        panel.add(new JScrollPane(transactions_list), BorderLayout.CENTER);

        return panel;
    }

    private void refresh_users() {
        users_list_model.clear();
        user_combo_box.removeAllItems();

        for (User user : bank_service.get_users()) {
            users_list_model.addElement(
                    user.get_first_name() + " " + user.get_last_name() + " | " + user.get_email()
            );
            user_combo_box.addItem(user);
        }
    }

    private void refresh_accounts() {
        accounts_list_model.clear();

        for (BankAccount account : bank_service.get_accounts()) {
            accounts_list_model.addElement(
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

    private void refresh_transactions() {
        transactions_list_model.clear();

        for (Transaction transaction : bank_service.get_transactions()) {
            transactions_list_model.addElement(transaction.toString());
        }
    }

    private void save_data() {
        storage.save(bank_service.export_data());
    }
}