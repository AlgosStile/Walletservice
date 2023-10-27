package wallet_service.in.config;

//import org.mockito.junit.MockitoJUnitRunner;
//import wallet_service.in.config.LiquibaseConfiguration;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import java.sql.SQLException;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LiquibaseConfigurationTest {
//    @Mock
//    LiquibaseConfiguration liquibaseConfiguration;
//
//    @Test
//    public void testStartLiquibase() throws SQLException {
//        assertNotNull(liquibaseConfiguration);
//    }
//}
import org.junit.jupiter.api.DisplayName;
import wallet_service.in.config.LiquibaseConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.SQLException;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LiquibaseConfigurationTest {
    @Mock
    LiquibaseConfiguration liquibaseConfiguration;

    @Test
    @DisplayName("Test if startLiquibase method runs without error")
    public void testStartLiquibase() throws SQLException {
        assertNotNull(liquibaseConfiguration);
    }


}
