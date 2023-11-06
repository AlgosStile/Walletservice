package wallet_service.out.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import wallet_service.out.model.Action;
import wallet_service.out.repository.ActionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ActionRepositoryTest {

    @Autowired
    private ActionRepository actionRepository;

    @Test
    public void testSaveAction() {
        Action action = new Action(1, "username", "action", "detail");
        actionRepository.saveAction(action);
        List<Action> actions = actionRepository.findByUsername("username");
        assertEquals(1, actions.size());
        assertEquals("username", actions.get(0).getUsername());
        assertEquals("action", actions.get(0).getAction());
        assertEquals("detail", actions.get(0).getDetail());
    }
}