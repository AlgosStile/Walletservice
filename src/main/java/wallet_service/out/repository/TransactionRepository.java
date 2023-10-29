package wallet_service.in.repository;

import wallet_service.in.model.Transaction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;
import java.util.Map;

/**
 * Репозиторий транзакций.
 *
 * Этот класс представляет репозиторий транзакций, который хранит информацию о транзакциях в системе.
 * Позволяет добавлять и получать данные о транзакциях.
 *
 * @author Олег Тодор
 */
public class TransactionRepository {
    private Map<String, Transaction> transactions;

    /**
     * Создает экземпляр класса TransactionRepository и инициализирует коллекцию транзакций.
     */
    public TransactionRepository() {
        this.transactions = new ConcurrentHashMap<>();
    }

    /**
     * Добавляет транзакцию в репозиторий.
     *
     * @param transaction Транзакция для добавления.
     */
    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
    }

    /**
     * Возвращает транзакцию по указанному идентификатору.
     *
     * @param id Идентификатор транзакции.
     * @return Транзакция, соответствующая указанному идентификатору, или null, если не найдена.
     */
    public Transaction getTransaction(String id) {
        return transactions.get(id);
    }

    /**
     * Возвращает коллекцию всех транзакций в репозитории.
     *
     * @return Коллекция транзакций.
     */
    public Collection<Transaction> getAllTransactions() {
        return transactions.values();
    }
}