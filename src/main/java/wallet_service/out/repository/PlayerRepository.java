package wallet_service.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Player;

import java.math.BigDecimal;

/**
 * Репозиторий для работы с сущностью Player.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Находит игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return игрок
     */
    Player findByUsername(String username);

    /**
     * Обновляет баланс игрока с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @param balance  новый баланс
     */
    void updateBalanceByUsername(String username, BigDecimal balance);
}