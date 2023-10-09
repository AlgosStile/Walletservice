package wallet_service.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActionTest {

    @Before
    public void setUp() throws Exception {
    }


    /**
     * testGetUsername() Проверяет,
     * что метод getUsername() класса Action возвращает правильное имя пользователя.
     */
    @Test
    public void testGetUsername() {
        Action action = new Action("username", "action", "detail");

        String username = action.getUsername();

        assertEquals("username", username);
    }

    /**
     * testGetAction() Проверяет, что метод getAction() класса Action возвращает
     * правильное действие.
     */
    @Test
    public void testGetAction() {

        Action actionInstance = new Action("username", "action", "detail");

        String actionResult = actionInstance.getAction();

        assertEquals("action", actionResult);
    }

    /**
     * testGetDetail() Проверяет, что метод getDetail() класса Action
     * возвращает правильную детализацию действия.
     */

    @Test
    public void testGetDetail() {
        Action action = new Action("username", "action", "detail");

        String detail = action.getDetail();

        assertEquals("detail", detail);
    }
}