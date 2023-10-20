package wallet_service.in.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {

    @Test
    @DisplayName("Test Player GetUsername")
    public void testGetUsername() throws Exception {
        Player player = new Player("testUser", "testPassword");
        assertEquals("testUser", player.getUsername());
    }

    @Test
    @DisplayName("Test Player GetPassword")
    public void testGetPassword() throws Exception {
        Player player = new Player("testUser", "testPassword");
        assertEquals("testPassword", player.getPassword());
    }
}
