package wallet_service.out.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wallet_service.out.repository.PlayerRepository;
import wallet_service.out.repository.TransactionRepository;

public class PlayerServiceImplTest {

    @Test
    public void testRegisterUserExistingUsername() {
        PlayerRepository playerRepository = new PlayerRepository();
        TransactionRepository transactionRepository = new TransactionRepository();
        PlayerService playerService = new PlayerServiceImpl(playerRepository, transactionRepository);

        playerService.registerPlayer("user1", "password1");
        Assertions.assertThrows(RuntimeException.class, () -> {
            playerService.registerPlayer("user1", "password2");
        });
    }
}