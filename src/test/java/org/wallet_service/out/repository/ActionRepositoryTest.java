package test.java.org.wallet_service.out.repository;

import main.java.org.wallet_service.out.model.Action;
import main.java.org.wallet_service.out.repository.ActionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

class ActionRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ActionRepository actionRepository;
    private Action action;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        actionRepository = new ActionRepository(jdbcTemplate);
        action = new Action();
        action.setUsername("John");
        action.setAction("Play");
        action.setDetail("Level 1");
    }

    @Test
    @DisplayName("Save action in database")
    void saveAction() {
        actionRepository.saveAction(action);
        verify(jdbcTemplate, times(1))
                .update("INSERT INTO actions (username, action, detail) VALUES (?, ?, ?)",
                        action.getUsername(), action.getAction(), action.getDetail());
    }

    @Test
    @DisplayName("Find all actions by username")
    void findByUsername() {
        List<Action> expectedActions = Arrays.asList(action);
        when(jdbcTemplate.query(anyString(), any(Object[].class), any(BeanPropertyRowMapper.class)))
                .thenReturn(expectedActions);

        List<Action> resultActions = actionRepository.findByUsername(action.getUsername());

        verify(jdbcTemplate, times(1)).query(
                eq("SELECT * FROM actions WHERE username = ?"),
                eq(new Object[]{"John"}),
                any(BeanPropertyRowMapper.class)
        );

        assertEquals(expectedActions, resultActions);
    }
}
