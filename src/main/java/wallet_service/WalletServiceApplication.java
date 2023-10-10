package wallet_service;

import wallet_service.model.Action;
import wallet_service.model.Transaction;
import wallet_service.repository.PlayerRepository;
import wallet_service.repository.TransactionRepository;
import wallet_service.service.PlayerService;

import java.util.Scanner;

/**
 * Класс WalletServiceApplication представляет собой интерфейс командной строки для приложения кошелька.
 * Он позволяет пользователям зарегистрироваться, аутентифицироваться и выполнять транзакции на своем аккаунте, а также просматривать историю своих транзакций и действий.
 * Класс использует объект PlayerService для обработки всей бизнес-логики и объект PlayerRepository для хранения данных игроков.
 * Класс содержит следующие методы:
 * main - главный метод, который запускает приложение и обрабатывает пользовательский ввод.
 * registerPlayer - метод для регистрации нового игрока.
 * authenticatePlayer - метод для аутентификации игрока.
 * getBalance - метод для получения баланса игрока.
 * debitTransaction - метод для выполнения дебетовой транзакции.
 * creditTransaction - метод для выполнения кредитной транзакции.
 * displayTransactionHistory - метод для отображения истории транзакций игрока.
 * logoutPlayer - метод для выхода игрока из системы.
 * displayActionHistory - метод для отображения истории действий игрока.
 * readLineFromUser - метод для чтения строки ввода от пользователя.
 * readDoubleFromUser - метод для чтения числа с плавающей точкой ввода от пользователя.
 * shutdown - метод для завершения работы приложения.
 *
 * @author Олег Тодор
 */
public class WalletServiceApplication {
    private static final String
            MENU_ITEM_1 = "1. Регистрировать игрока",
            MENU_ITEM_2 = "2. Аутентифицировать игрока",
            MENU_ITEM_3 = "3. Баланс",
            MENU_ITEM_4 = "4. Дебет",
            MENU_ITEM_5 = "5. Кредит",
            MENU_ITEM_6 = "6. История транзакций",
            MENU_ITEM_7 = "7. Завершение работы игрока",
            MENU_ITEM_8 = "8. История действий",
            MENU_ITEM_9 = "9. Выход";

    private static PlayerService playerService;
    private static Scanner scanner;
    private static PlayerRepository playerRepository;


    public static void main(String[] args) {
        playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        playerService = new PlayerService(playerRepository, transactionRepository);
        scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            String menu = String.join("\n", MENU_ITEM_1, MENU_ITEM_2, MENU_ITEM_3,
                    MENU_ITEM_4, MENU_ITEM_5, MENU_ITEM_6, MENU_ITEM_7, MENU_ITEM_8, MENU_ITEM_9);

            System.out.println(menu);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerPlayer();
                    break;
                case 2:
                    authenticatePlayer();
                    break;
                case 3:
                    getBalance();
                    break;
                case 4:
                    debitTransaction();
                    break;
                case 5:
                    creditTransaction();
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

    private static void registerPlayer() {
        String username = readLineFromUser("Введите имя пользователя: ");
        String password = readLineFromUser("Введите пароль: ");
        playerService.registerPlayer(username, password);
        System.out.println("Игрок успешно зарегистрировался");
    }

    private static void authenticatePlayer() {
        String username = readLineFromUser("Введите имя пользователя: ");
        String password = readLineFromUser("Введите пароль: ");
        if (playerService.authenticatePlayer(username, password)) {
            System.out.println("Игрок успешно авторизован!");
        } else {
            System.out.println("Неправильное имя пользователя или пароль");
        }
    }

    private static void getBalance() {
        String username = readLineFromUser("Введите имя пользователя: ");
        double balance = playerService.getBalance(username);
        System.out.println("Баланс: " + balance);
    }

    private static void debitTransaction() {
        String username = readLineFromUser("Введите имя пользователя: ");
        String transactionId = readLineFromUser("Введите ID транзакции: ");
        double amount = readDoubleFromUser("Введите сумму: ");

        try {
            playerService.debit(username, transactionId, amount);
            System.out.println("Дебетовая транзакция прошла успешно");
        } catch (Exception e) {
            System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
        }
    }

    private static void creditTransaction() {
        String username = readLineFromUser("Введите имя пользователя: ");
        String transactionId = readLineFromUser("Введите ID транзакции: ");
        double amount = readDoubleFromUser("Введите сумму: ");

        try {
            playerService.credit(username, transactionId, amount);
            System.out.println("Кредитная транзакция прошла успешно --> $");
        } catch (Exception e) {
            System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
        }
    }

    private static void displayTransactionHistory() {
        String username = readLineFromUser("Введите имя пользователя: ");

        for (Transaction transaction : playerService.getTransactionHistory(username)) {
            System.out.println(transaction.getType() + " "
                    + transaction.getAmount() + " " + transaction.getId());
        }
    }

    private static void logoutPlayer() {
        String username = readLineFromUser("Введите имя пользователя: ");
        playerService.logout(username);
        System.out.println("Игрок" + " " + username + " успешно вышел из системы");

        if (playerRepository.getAllPlayers().isEmpty()) {
            System.out.println("В системе больше нет игроков. Завершение работы...");
            System.exit(0);
        }
    }

    private static void displayActionHistory() {
        String username = readLineFromUser("Введите имя пользователя: ");
        for (Action action : playerService.getPlayerActions(username)) {
            System.out.println(action.getAction() + " " + action.getDetail());
        }
    }

    private static String readLineFromUser(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static double readDoubleFromUser(String prompt) {
        System.out.print(prompt);
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    private static void shutdown() {
        System.out.println("Завершение работы...");
    }
}

///**
// * Класс WalletServiceApplication представляет приложение для управления кошельками игроков.
// *
// * @author Олег Тодор
// */
//public class WalletServiceApplication {
//
//    /**
//     * Точка входа в приложение.
//     */
//    private static final String MENU_ITEM_1 = "1. Регистрировать игрока";
//    private static final String MENU_ITEM_2 = "2. Аутентифицировать игрока";
//    private static final String MENU_ITEM_3 = "3. Получить баланс";
//    private static final String MENU_ITEM_4 = "4. Дебет";
//    private static final String MENU_ITEM_5 = "5. Кредит";
//    private static final String MENU_ITEM_6 = "6. История транзакций";
//    private static final String MENU_ITEM_7 = "7. Завершить";
//    private static final String MENU_ITEM_8 = "8. История действий";
//
//    public static void main(String[] args) {
//        PlayerRepository playerRepository = new PlayerRepository();
//        TransactionRepository transactionRepository = new TransactionRepository();
//        PlayerService playerService = new PlayerService(playerRepository, transactionRepository);
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            String menu = MENU_ITEM_1 + "\n" + MENU_ITEM_2 + "\n" + MENU_ITEM_3 + "\n" + MENU_ITEM_4 + "\n" + MENU_ITEM_5 + "\n" + MENU_ITEM_6 + "\n" + MENU_ITEM_7 + "\n" + MENU_ITEM_8;
//            System.out.println(menu);
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    System.out.print("Введите имя пользователя: ");
//                    String username = scanner.nextLine();
//                    System.out.print("Введите пароль: ");
//                    String password = scanner.nextLine();
//                    playerService.registerPlayer(username, password);
//                    System.out.println("Игрок успешно зарегистрировался");
//                    break;
//                case 2:
//                    System.out.print("Введите имя пользователя: ");
//                    String authUsername = scanner.nextLine();
//                    System.out.print("Введите пароль: ");
//                    String authPassword = scanner.nextLine();
//                    boolean authenticated = playerService.authenticatePlayer(authUsername, authPassword);
//                    if (authenticated) {
//                        System.out.println("Игрок успешно авторизован!");
//                    } else {
//                        System.out.println("Неправильное имя пользователя или пароль");
//                    }
//                    break;
//                case 3:
//                    System.out.print("Введите имя пользователя: ");
//                    String balanceUsername = scanner.nextLine();
//                    double balance = playerService.getBalance(balanceUsername);
//                    System.out.println("Баланс: " + balance);
//                    break;
//                case 4:
//                    System.out.print("Введите имя пользователя: ");
//                    String debitUsername = scanner.nextLine();
//                    System.out.print("Введите ID транзакции: ");
//                    String debitTransactionId = scanner.nextLine();
//                    System.out.print("Введите сумму: ");
//                    double debitAmount = scanner.nextDouble();
//                    scanner.nextLine();
//                    try {
//                        playerService.debit(debitUsername, debitTransactionId, debitAmount);
//                        System.out.println("Дебетовая транзакция прошла успешно");
//                    } catch (Exception e) {
//                        System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
//                    }
//                    break;
//                case 5:
//                    System.out.print("Введите имя пользователя: ");
//                    String creditUsername = scanner.nextLine();
//                    System.out.print("Введите ID транзакции: ");
//                    String creditTransactionId = scanner.nextLine();
//                    System.out.print("Введите сумму: ");
//                    double creditAmount = scanner.nextDouble();
//                    scanner.nextLine();
//                    try {
//                        playerService.credit(creditUsername, creditTransactionId, creditAmount);
//                        System.out.println("Кредитная транзакция прошла успешно --> $");
//                    } catch (Exception e) {
//                        System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
//                    }
//                    break;
//                case 6:
//                    System.out.print("Введите имя пользователя: ");
//                    String historyUsername = scanner.nextLine();
//                    List<Transaction> transactions = playerService.getTransactionHistory(historyUsername);
//                    for (Transaction transaction : transactions) {
//                        System.out.println(transaction.getType() + " " + transaction.getAmount() + " " + transaction.getId());
//                    }
//                    break;
//                /**
//                 * Выход пользователя из системы и завершение работы программы, если все игроки вышли из системы.
//                 *
//                 * @param scanner объект Scanner для чтения ввода пользователя
//                 * @param playerService объект PlayerService для управления игроками
//                 * @param playerRepository объект PlayerRepository для доступа к данным игроков
//                 */
//                case 7:
//                    System.out.print("Введите имя пользователя: ");
//                    String logoutUsername = scanner.nextLine();
//                    playerService.logout(logoutUsername);
//                    System.out.println("Игрок" + " " + logoutUsername + " успешно вышел из системы");
//                    if (playerRepository.getAllPlayers().isEmpty()) {
//                        System.out.println("В системе больше нет игроков. Завершение работы...");
//                        System.exit(0);
//                    }
//
//                case 8:
//                    System.out.print("Введите имя пользователя: ");
//                    String actionUsername = scanner.nextLine();
//                    List<Action> actions = playerService.getPlayerActions(actionUsername);
//                    for (Action action : actions) {
//                        System.out.println(action.getAction() + " " + action.getDetail());
//                    }
//                    break;
//                default:
//                    System.out.println("Неверный выбор");
//            }
//        }
//    }
//}