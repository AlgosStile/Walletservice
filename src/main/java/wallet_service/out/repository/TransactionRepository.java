package wallet_service.out.repository;

import wallet_service.in.model.Transaction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Map;

/**
 * Класс, представляющий репозиторий транзакций.
 *
 * @author Олег Тодор
 */
public class TransactionRepository {
    private Map<String, Transaction> transactions; // Коллекция транзакций

    /**
     * Конструктор класса TransactionRepository.
     */
    public TransactionRepository() {
        this.transactions = new ConcurrentHashMap<>();
    }

    /**
     * Добавить транзакцию в репозиторий.
     *
     * @param transaction Транзакция для добавления
     */
    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    /**
     * Получить транзакцию по идентификатору.
     *
     * @param id Идентификатор транзакции
     * @return Транзакция с указанным идентификатором или null, если транзакция не найдена
     */
    public Transaction getTransaction(String id) {
        return transactions.get(id);
    }

    /**
     * Получить все транзакции.
     *
     * @return Коллекция всех транзакций
     */
    public Collection<Transaction> getAllTransactions() {
        return transactions.values();
    }
}