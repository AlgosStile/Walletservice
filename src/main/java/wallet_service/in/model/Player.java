package wallet_service.in.model;

import wallet_service.in.controller.TransactionType;
import wallet_service.in.repository.PlayerRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Player {
    private int id;
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

    public Player() {
        this.transactions = new ArrayList<>();
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }


    public synchronized void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }


    public void debit(int transactionId, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Сумма не может быть отрицательной");
        }
        if (amount > this.balance) {
            throw new Exception("Недостаточно средств");
        }

        this.balance -= amount;
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.DEBIT);
        transactions.add(transaction);
    }


    public void credit(int transactionId, double amount) throws Exception {
        if (amount < 0) {
            throw new Exception("Некорректная сумма");
        }
        this.balance += amount;
        Transaction transaction = new Transaction(transactionId, amount, TransactionType.CREDIT);
        transactions.add(transaction);
    }

    public double getBalance() {
        return balance;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }
}