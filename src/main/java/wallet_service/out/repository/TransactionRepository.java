package wallet_service.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Transaction;

import java.util.List;


/**
 * Репозиторий для работы с сущностью Transaction.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Находит все транзакции для игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return список транзакций
     */
    List<Transaction> findByPlayerUsername(String username);
}