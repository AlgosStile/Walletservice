package wallet_service.in.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ActionTest {

    @Test
    @DisplayName("Test Action GetUsername")
    public void testGetUsername() {
        Action action = new Action("testUser", "testAction", "testDetail");
        assertEquals("testUser", action.getUsername());
    }

    @Test
    @DisplayName("Test Action GetAction")
    public void testGetAction() {
        Action action = new Action("testUser", "testAction", "testDetail");
        assertEquals("testAction", action.getAction());
    }
}
