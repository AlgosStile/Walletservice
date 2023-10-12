package wallet_service;

import wallet_service.in.controller.PlayerController;
import wallet_service.in.controller.TransactionController;
import wallet_service.in.model.Action;
import wallet_service.in.model.Transaction;
import wallet_service.in.service.PlayerService;
import wallet_service.in.service.PlayerServiceImpl;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Класс WalletServiceApplication представляет собой командно-строковый интерфейс банковского сервиса,
 * осуществляющего управление счетами игроков. Пользователи могут регистрироваться, аутентифицироваться,
 * проводить транзакции и просматривать историю транзакций и действий.
 * Это главный класс приложения, он использует PlayerService для бизнес-логики и PlayerRepository для хранения данных.
 *
 * @author Олег Тодор
 */
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

    public static void main(String[] args) throws Exception {
        PlayerRepository playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        PlayerService playerService = new PlayerServiceImpl(playerRepository, transactionRepository);

        WalletServiceApplication application = new WalletServiceApplication(playerService);
        application.run();
    }

    public WalletServiceApplication(PlayerService playerService) {
        scanner = new Scanner(System.in);

        playerController = new PlayerController(playerService);
        transactionController = new TransactionController(playerService);
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
                    String transactionId4 = readLineFromUser("Введите ID транзакции: ");
                    double amount4 = readDoubleFromUser("Введите сумму: ");
                    transactionController.debitTransaction(username4, transactionId4, amount4);
                    break;
                case 5:
                    String username5 = readLineFromUser("Введите имя пользователя: ");
                    String transactionId5 = readLineFromUser("Введите ID транзакции: ");
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


    private void displayTransactionHistory() {
        String username = readLineFromUser("Введите имя пользователя: ");
        List<Transaction> transactions = transactionController.getTransactionHistory(username);
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
    }

}

