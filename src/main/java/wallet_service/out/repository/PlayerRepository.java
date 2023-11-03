package wallet_service.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_service.out.model.Player;

import java.math.BigDecimal;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {


    Player findByUsername(String username);


    void updateBalanceByUsername(String username, BigDecimal balance);
}