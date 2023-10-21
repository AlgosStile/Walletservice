package wallet_service.in.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ConfigTest {
    private Config config;

    @Before
    public void setUp() {
        config = Config.getInstance();
    }

    @Test
    @DisplayName("getProperty_ShouldReturnNullForNonExistingKey")
    public void getProperty_ShouldReturn() {
        String actualValue = config.getProperty("/db/changelog/liquibase.properties");
        String expectedValue = null;
        assertEquals(expectedValue, actualValue);
    }

    @Test
    @DisplayName("getProperty_ShouldReturnNullForNonExistingKey")
    public void getProperty_Sh() {
        String actualValue = config.getProperty("/db/changelog/liquibase.properties");
        assertNull(actualValue);
    }
}
