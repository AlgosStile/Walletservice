package test.java.wallet_service.out.repository;


import main.java.org.wallet_service.out.repository.ActionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import main.java.org.wallet_service.out.model.Action;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ActionRepository.class)
public class ActionRepositoryTest {

    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {

        clearActions();
    }

    @Test
    @DisplayName("Test save action")
    public void testSaveAction() {
        Action action = new Action(1, "username", "action", "detail");
        jdbcTemplate.update("INSERT INTO players (username, password, balance) VALUES (?, ?, ?)", "username", "password", 0);

        actionRepository.saveAction(action);

        List<Action> actions = actionRepository.findByUsername("username");

        assertEquals(1, actions.size());
        assertEquals("action", actions.get(0).getAction());
        assertEquals("detail", actions.get(0).getDetail());
    }

    @Test
    @DisplayName("FindByUsername_WithNoMatchingActions")
    public void testFindByUsername_WithNoMatchingActions() {
        String username = "nonexistent";

        List<Action> actions = actionRepository.findByUsername(username);

        assertEquals(0, actions.size());
    }

    @Test
    @DisplayName("FindByUsername_WithMultipleMatchingActions")
    public void testFindByUsername_WithMultipleMatchingActions() {
        jdbcTemplate.update("INSERT INTO players (username, password, balance) VALUES (?, ?, ?)", "username", "password", 0);

        Action action1 = new Action(1, "username", "action1", "detail1");
        Action action2 = new Action(2, "username", "action2", "detail2");
        Action action3 = new Action(3, "username", "action3", "detail3");

        actionRepository.saveAction(action1);
        actionRepository.saveAction(action2);
        actionRepository.saveAction(action3);

        List<Action> actions = actionRepository.findByUsername("username");

        assertEquals(3, actions.size());
        assertEquals("action1", actions.get(0).getAction());
        assertEquals("detail1", actions.get(0).getDetail());
        assertEquals("action2", actions.get(1).getAction());
        assertEquals("detail2", actions.get(1).getDetail());
        assertEquals("action3", actions.get(2).getAction());
        assertEquals("detail3", actions.get(2).getDetail());
    }

    private void clearActions() {

        actionRepository.deleteAll();
    }
}