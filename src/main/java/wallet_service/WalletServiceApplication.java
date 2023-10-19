package wallet_service;


import wallet_service.in.config.DBConnection;
import wallet_service.in.config.LiquibaseConfiguration;
import wallet_service.in.controller.PlayerController;
import wallet_service.in.controller.TransactionController;
import wallet_service.in.model.Action;
import wallet_service.in.model.Transaction;
import wallet_service.in.repository.PlayerRepository;
import wallet_service.in.repository.TransactionRepository;
import wallet_service.in.service.PlayerService;
import wallet_service.in.service.PlayerServiceImpl;

import java.sql.SQLException;
import java.util.List;;
import java.util.Scanner;

public class WalletServiceApplication {
  
    private static final String MENU_ITEM_1 = "1. Регистрировать игрока";
    private static final String MENU_ITEM_2 = "2. Аутентифицировать игрока";
    private static final String MENU_ITEM_3 = "3. Баланс";
    private static final String MENU_ITEM_4 = "4. Дебетовая операция по счету";
    private static final String MENU_ITEM_5 = "5. Кредитная операция по счету";
    private static final String MENU_ITEM_6 = "6. История транзакций";
    private static final String MENU_ITEM_7 = "7. Завершение работы игрока";
    private static final String MENU_ITEM_8 = "8. История действий";
    private static final String MENU_ITEM_9 = "9. Выход";

    private static Scanner scanner;
    private static TransactionController transactionController;
    private static PlayerController playerController;
    private static PlayerRepository playerRepository;
    private static TransactionRepository transactionRepository;

    public static void main(String[] args) throws Exception {
        LiquibaseConfiguration.startLiquibase();
        playerRepository = PlayerRepository.getInstance();
        transactionRepository = TransactionRepository.getInstance(playerRepository);
        PlayerService playerService = new PlayerServiceImpl(playerRepository, transactionRepository);
        WalletServiceApplication application = new WalletServiceApplication(playerService, playerRepository, transactionRepository);
        application.run();

    }


    public WalletServiceApplication(PlayerService playerService, PlayerRepository playerRepository, TransactionRepository transactionRepository) throws SQLException {
        scanner = new Scanner(System.in);
        playerController = new PlayerController(playerService, playerRepository);
        transactionController = new TransactionController(playerService, playerRepository, transactionRepository);
    }


    public void run() throws Exception {
        boolean running = true;
        while (running) {
            String menu = String.join("\n", MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3, MENU_ITEM_4, MENU_ITEM_5, MENU_ITEM_6, MENU_ITEM_7, MENU_ITEM_8, MENU_ITEM_9);

            System.out.println(menu);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String username1 = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password1 = scanner.nextLine();
                    playerController.registerPlayer(username1, password1);
                    break;
                case 2:
                    String username2 = readLineFromUser("Введите имя пользователя: ");
                    String password2 = readLineFromUser("Введите пароль: ");
                    playerController.authenticatePlayer(username2, password2);
                    break;
                case 3:
                    String username3 = readLineFromUser("Введите имя пользователя: ");
                    playerController.getBalance(username3);
                    break;
                case 4:
                    String username4 = readLineFromUser("Введите имя пользователя: ");
                    System.out.print("Введите ID транзакции: ");  // Добавьте запрос на ввод ID транзакции
                    int transactionId4 = scanner.nextInt(); // Считайте ID транзакции как целое число
                    scanner.nextLine();
                    double amount4 = readDoubleFromUser("Введите сумму: ");
                    transactionController.debitTransaction(username4, transactionId4, amount4);
                    break;
                case 5:
                    String username5 = readLineFromUser("Введите имя пользователя: ");
                    System.out.print("Введите ID транзакции: ");
                    int transactionId5 = scanner.nextInt();
                    scanner.nextLine();
                    double amount5 = readDoubleFromUser("Введите сумму: ");
                    transactionController.creditTransaction(username5, transactionId5, amount5);
                    break;
                case 6:
                    displayTransactionHistory();
                    break;
                case 7:
                    logoutPlayer();
                    break;
                case 8:
                    displayActionHistory();
                    break;
                case 9:
                    shutdown();
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }



    private void displayTransactionHistory() throws SQLException {
        String username = readLineFromUser("Введите имя пользователя: ");
        List<Transaction> transactions = transactionController.getTransactionHistory(username); // Здесь вызывается getTransactionHistory
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getType() + " " + transaction.getAmount() + " " + transaction.getId());
        }
    }

    private void logoutPlayer() {
        String username = readLineFromUser("Введите имя пользователя: ");
        playerController.logoutPlayer(username);
        System.out.println("Игрок" + " " + username + " успешно вышел из системы");
    }

    private void displayActionHistory() {
        String username = readLineFromUser("Введите имя пользователя: ");
        List<Action> actions = playerController.getPlayerActions(username);
        for (Action action : actions) {
            System.out.println(action.getAction() + " " + action.getDetail());
        }
    }

    private String readLineFromUser(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    private double readDoubleFromUser(String prompt) {
        System.out.print(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private void shutdown() {
        System.out.println("Завершение работы...");
        scanner.close();
    }


}

