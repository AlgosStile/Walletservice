package wallet_service.in.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Get Username Test")
    public void GetUsernameTest() {
        Action action = new Action("username", "action", "detail");

        String username = action.getUsername();

        assertEquals("username", username);
    }

    @Test
    @DisplayName("Get Action Test")
    public void GetActionTest() {

        Action actionInstance = new Action("username", "action", "detail");

        String actionResult = actionInstance.getAction();

        assertEquals("action", actionResult);
    }

    @Test
    @DisplayName("Get Detail Test")
    public void GetDetailTest() {
        Action action = new Action("username", "action", "detail");

        String detail = action.getDetail();

        assertEquals("detail", detail);
    }
}
