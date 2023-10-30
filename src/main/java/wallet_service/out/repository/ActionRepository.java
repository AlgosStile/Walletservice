package wallet_service.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Action;

import java.util.List;
/**
 * Репозиторий для работы с сущностью Action.
 */
@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    /**
     * Находит все действия с указанным именем пользователя.
     *
     * @param username имя пользователя
     * @return список действий
     */
    List<Action> findByUsername(String username);
}
