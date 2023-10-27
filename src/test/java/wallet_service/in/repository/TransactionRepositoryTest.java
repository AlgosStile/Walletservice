package wallet_service.in.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import wallet_service.in.config.DBConnection;
import wallet_service.in.controller.TransactionType;
import wallet_service.in.model.Player;
import wallet_service.in.model.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRepositoryTest {
    @Mock
    DBConnection dbConnection;

    @Mock
    Connection connection;

    @Mock
    PreparedStatement preparedStatement;

    @Mock
    ResultSet resultSet;

    @Mock
    PlayerRepository playerRepository;

    TransactionRepository transactionRepository;

    @Before
    public void setUp() throws Exception {
        Mockito.when(dbConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(playerRepository.getPlayer(anyString())).thenReturn(new Player("testUser", "testPassword"));
        transactionRepository = TransactionRepository.getInstance(playerRepository);
    }

    @Test
    @DisplayName("Add Transaction Successfully")
    public void addTransactionSuccessfully() throws Exception {
        Transaction transaction = new Transaction(1, 500, TransactionType.DEBIT);
        transactionRepository.addTransaction("testUser", transaction);
    }


}

