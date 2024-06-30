public class Transaction {
    private String type;
    private String accountNumber;
    private double amount;
    private double newBalance;

    public Transaction(String type, String accountNumber, double amount, double newBalance) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.newBalance = newBalance;
    }

    public String getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public double getNewBalance() {
        return newBalance;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", newBalance=" + newBalance +
                '}';
    }
}
