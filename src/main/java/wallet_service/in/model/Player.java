package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;

import java.util.ArrayList;
import java.util.List;



public class Player {
    private String username; // Имя пользователя
    private String password; // Пароль
    private double balance; // Баланс
    private List<Transaction> transactions; // Список транзакций


    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public double getBalance() {
        return balance;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }


    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }


    public void debit(String id, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Сумма не может быть отрицательной");
        }
        if (amount > this.balance) {
            throw new Exception("Недостаточно средств");
        }

        this.balance -= amount;
        Transaction transaction = new Transaction(id, amount, TransactionType.DEBIT);
        transactions.add(transaction);
    }


    public void credit(String id, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Некорректная сумма");
        }
        this.balance += amount;
        Transaction transaction = new Transaction(id, amount, TransactionType.CREDIT);
        transactions.add(transaction);
    }
}