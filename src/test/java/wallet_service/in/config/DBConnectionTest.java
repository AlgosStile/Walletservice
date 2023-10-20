package wallet_service.in.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DBConnectionTest {

    private DBConnection dbConnection;

    @Before
    public void setUp() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (Exception e) {
            fail("DBConnection should be instantiated without errors");
        }
    }

    @Test
    @DisplayName("dbConnection_ShouldNotBeNull")
    public void dbConnection__() {
        assertNotNull(dbConnection);
    }

    @Test
    @DisplayName("getConnection_ShouldReturnConnection")
    public void getConnection_S() {
        Connection connection = dbConnection.getConnection();
        assertNotNull(connection);
    }
}
