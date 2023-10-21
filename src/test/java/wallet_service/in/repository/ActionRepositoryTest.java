package wallet_service.in.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class ActionRepositoryTest {
    @Mock
    DBConnection dbConnection;

    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    ActionRepository actionRepository;

    @Before
    public void setUp() throws Exception {
        Mockito.when(dbConnection.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        actionRepository = new ActionRepository();
    }

    @Test
    @DisplayName("Add Action Successfully")
    public void addActionSuccessfully() throws Exception {
        Action action = new Action("testUser", "testAction", "testDetail");
        actionRepository.addAction(action);
    }

}


