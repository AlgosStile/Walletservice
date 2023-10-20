package wallet_service.in.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class PlayerRepositoryTest {
    @Mock
    DBConnection dbConnection;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    PlayerRepository playerRepository;

    @Before
    public void setUp() throws Exception {
        Mockito.when(dbConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        playerRepository = PlayerRepository.getInstance();
    }

    @Test
    @DisplayName("Add Player Successfully")
    public void addPlayerSuccessfully() throws Exception {
        Player player = new Player("testUser", "testPassword");
        playerRepository.addPlayer(player);
    }

}
