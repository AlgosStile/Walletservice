package wallet_service;

import wallet_service.model.Action;
import wallet_service.model.Transaction;
import wallet_service.repository.PlayerRepository;
import wallet_service.repository.TransactionRepository;
import wallet_service.service.PlayerService;

import java.util.List;
import java.util.Scanner;

/**
 * Класс WalletServiceApplication представляет приложение для управления кошельками игроков.
 */
public class WalletServiceApplication {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        PlayerRepository playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        PlayerService playerService = new PlayerService(playerRepository, transactionRepository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Регистрировать игрока");
            System.out.println("2. Аутентифицировать игрока");
            System.out.println("3. Получить баланс");
            System.out.println("4. Дебет");
            System.out.println("5. Кредит");
            System.out.println("6. История транзакций");
            System.out.println("7. Завершить");
            System.out.println("8. История действий");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String username = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String password = scanner.nextLine();
                    playerService.registerPlayer(username, password);
                    System.out.println("Игрок успешно зарегистрировался");
                    break;
                case 2:
                    System.out.print("Введите имя пользователя: ");
                    String authUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String authPassword = scanner.nextLine();
                    boolean authenticated = playerService.authenticatePlayer(authUsername, authPassword);
                    if (authenticated) {
                        System.out.println("Игрок успешно авторизован!");
                    } else {
                        System.out.println("Неправильное имя пользователя или пароль");
                    }
                    break;
                case 3:
                    System.out.print("Введите имя пользователя: ");
                    String balanceUsername = scanner.nextLine();
                    double balance = playerService.getBalance(balanceUsername);
                    System.out.println("Баланс: " + balance);
                    break;
                case 4:
                    System.out.print("Введите имя пользователя: ");
                    String debitUsername = scanner.nextLine();
                    System.out.print("Введите ID транзакции: ");
                    String debitTransactionId = scanner.nextLine();
                    System.out.print("Введите сумму: ");
                    double debitAmount = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        playerService.debit(debitUsername, debitTransactionId, debitAmount);
                        System.out.println("Дебетовая транзакция прошла успешно");
                    } catch (Exception e) {
                        System.out.println("Дебетовая транзакция не удалась: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Введите имя пользователя: ");
                    String creditUsername = scanner.nextLine();
                    System.out.print("Введите ID транзакции: ");
                    String creditTransactionId = scanner.nextLine();
                    System.out.print("Введите сумму: ");
                    double creditAmount = scanner.nextDouble();
                    scanner.nextLine();
                    try {
                        playerService.credit(creditUsername, creditTransactionId, creditAmount);
                        System.out.println("Кредитная транзакция прошла успешно --> $");
                    } catch (Exception e) {
                        System.out.println("Кредитная транзакция не удалась ¯\\_(ツ)_/¯ : " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("Введите имя пользователя: ");
                    String historyUsername = scanner.nextLine();
                    List<Transaction> transactions = playerService.getTransactionHistory(historyUsername);
                    for (Transaction transaction : transactions) {
                        System.out.println(transaction.getType() + " " + transaction.getAmount() + " " + transaction.getId());
                    }
                    break;
                case 7:
                    System.out.print("Введите имя пользователя: ");
                    String logoutUsername = scanner.nextLine();
                    playerService.logout(logoutUsername);
                    System.out.println("Игрок" + " " + logoutUsername + " успешно вышел из системы");
                    // проверяем, остался ли хотя бы один игрок
                    if (playerRepository.getAllPlayers().isEmpty()) {
                        System.out.println("Все игроки вышли из системы. Завершение работы...");
                        System.exit(0);
                    }

                case 8:
                    System.out.print("Введите имя пользователя: ");
                    String actionUsername = scanner.nextLine();
                    List<Action> actions = playerService.getPlayerActions(actionUsername);
                    for (Action action : actions) {
                        System.out.println(action.getAction() + " " + action.getDetail());
                    }
                    break;
                default:
                    System.out.println("Неверный выбор");
            }
        }
    }
}