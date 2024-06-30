import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;



public class BankingSystem extends JFrame implements ActionListener {
    private JTextField accountField, amountField, balanceField;
    private JPasswordField passwordField;
    private JButton createAccountBtn, depositBtn, withdrawBtn, viewTransactionsBtn,clearBtn;

    private HashMap<String, Double> accountBalances = new HashMap<>();
    private HashMap<String, String> accountPasswords = new HashMap<>();
    private List<Transaction> transactions = new ArrayList<>();
    private String currentAccount = null;

    public BankingSystem() {
        setTitle("Banking System");
        setSize(600, 300);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Account Number: "));
        accountField = new JTextField();
        inputPanel.add(accountField);

        inputPanel.add(new JLabel("Password: "));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        inputPanel.add(new JLabel("Amount: "));
        amountField = new JTextField();
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Balance: "));
        balanceField = new JTextField();
        balanceField.setEditable(false);
        inputPanel.add(balanceField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        createAccountBtn = new JButton("Create Account");
        depositBtn = new JButton("Deposit");
        withdrawBtn = new JButton("Withdraw");
        viewTransactionsBtn = new JButton("View Transactions");
	 clearBtn = new JButton("Clear");
        createAccountBtn.addActionListener(this);
        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        viewTransactionsBtn.addActionListener(this);
	clearBtn.addActionListener(this);
        buttonPanel.add(createAccountBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(viewTransactionsBtn);
	buttonPanel.add(clearBtn);
        add(buttonPanel, BorderLayout.SOUTH);
	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

   
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createAccountBtn) {
            createAccount();
        } else if (e.getSource() == depositBtn) {
            deposit();
        } else if (e.getSource() == withdrawBtn) {
            withdraw();
        } else if (e.getSource() == viewTransactionsBtn) {
            viewTransactions();
        } else if (e.getSource() == clearBtn) {
            clearFields();
        }
    }

    private void createAccount() {
        String accountNumber = accountField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        if (!accountNumber.isEmpty() && password.length() > 0) {
            accountBalances.put(accountNumber, 0.0);
            accountPasswords.put(accountNumber, password);
            updateBalanceField(accountNumber);
            showMessage("Account created successfully!", "");


            currentAccount = accountNumber;
        } else {
            showMessage("Please enter an account number and password.", "");
        }


        passwordField.setText(password);
    }

    private void deposit() {
        if (currentAccount == null) {
            showMessage("Please authenticate first.", "");
            return;
        }

        String amountStr = amountField.getText();
        char[] enteredPassword = passwordField.getPassword();
        String enteredPasswordString = new String(enteredPassword);

        // Check if the entered password matches the stored password
        String storedPassword = accountPasswords.get(currentAccount);
        if (!enteredPasswordString.equals(storedPassword)) {
            showMessage("Incorrect password. Deposit failed.", "");
            return;
        }


        if (!amountStr.isEmpty()) {
            double amount = Double.parseDouble(amountStr);
            double currentBalance = accountBalances.getOrDefault(currentAccount, 0.0);
            currentBalance += amount;
            accountBalances.put(currentAccount, currentBalance);
            updateBalanceField(currentAccount);

            String transactionDetails = "Transaction Type: Deposit\n" +
                    "Account Number: " + currentAccount + "\n" +
                    "Deposit Amount: ₹" + amount + "\n" +
                    "New Balance: ₹" + currentBalance;

            transactions.add(new Transaction("Deposit", currentAccount, amount, currentBalance));
            showMessage("Deposit successful!", transactionDetails);
        } else {
            showMessage("Please enter amount to deposit.", "");
        }



        passwordField.setText(enteredPasswordString);

       
    }

    private void withdraw() {
        if (currentAccount == null) {
            showMessage("Please authenticate first.", "");
            return;
        }

        String amountStr = amountField.getText();
        char[] enteredPassword = passwordField.getPassword();
        String enteredPasswordString = new String(enteredPassword);

        // Check if the entered password matches the stored password
        String storedPassword = accountPasswords.get(currentAccount);
        if (!enteredPasswordString.equals(storedPassword)) {
            showMessage("Incorrect password. Withdrawal failed.", "");
            return;
        }


        if (!amountStr.isEmpty()) {
            double amount = Double.parseDouble(amountStr);
            double currentBalance = accountBalances.getOrDefault(currentAccount, 0.0);
            if (amount <= currentBalance) {
                currentBalance -= amount;
                accountBalances.put(currentAccount, currentBalance);
                updateBalanceField(currentAccount);

                String transactionDetails = "Transaction Type: Withdrawal\n" +
                        "Account Number: " + currentAccount + "\n" +
                        "Withdrawal Amount: ₹" + amount + "\n" +
                        "New Balance: ₹" + currentBalance;

                transactions.add(new Transaction("Withdrawal", currentAccount, amount, currentBalance));
                showMessage("Withdrawal successful!", transactionDetails);
            } else {
                showMessage("Insufficient funds!", "");
            }
        } else {
            showMessage("Please enter an amount to withdraw.", "");
        }



        passwordField.setText(enteredPasswordString);

        amountField.setText("");
    }

    private void viewTransactions() {
        if (currentAccount == null) {
            showMessage("Please authenticate first.", "");
            return;
        }

        StringBuilder transactionHistory = new StringBuilder("Transaction History:\n");
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(currentAccount)) {
                transactionHistory.append(transaction).append("\n------------------------");
            }
        }
        showMessage(transactionHistory.toString(), "");
    }

    private void updateBalanceField(String accountNumber) {
        double currentBalance = accountBalances.getOrDefault(accountNumber, 0.0);
        balanceField.setText(String.valueOf(currentBalance));
    }

  private void showMessage(String message, String transactionDetails) {
        JFrame receiptFrame = new JFrame("Transaction Receipt");
        receiptFrame.setSize(300, 200);

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.append(message + "\n\nTransaction Details:\n" + transactionDetails);

        receiptFrame.add(receiptArea);
        receiptFrame.setLocationRelativeTo(this);
        receiptFrame.setVisible(true);
    }

    private void clearFields() {
        accountField.setText("");
        passwordField.setText("");
        amountField.setText("");
        balanceField.setText("");
        currentAccount = null; 
    }
public static void main(String[] args){
		new BankingSystem();
	}
}
