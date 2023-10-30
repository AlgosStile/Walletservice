package wallet_service.out.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wallet_service.out.model.Action;
import wallet_service.out.model.Player;
import wallet_service.out.service.PlayerServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {

    @Mock
    private PlayerServiceImpl playerService;

    private PlayerController playerController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        playerController = new PlayerController(playerService);
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetPlayer() throws Exception {
        String username = "john";
        Player player = new Player(username, "password", 100);

        when(playerService.getPlayer(username)).thenReturn(player);

        mockMvc.perform(get("/player/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.password").value("password"))
                .andExpect(jsonPath("$.balance").value(100));

        verify(playerService).getPlayer(username);
    }

    @Test
    public void testSavePlayer() throws Exception {
        Player player = new Player("john", "password", 100);

        mockMvc.perform(post("/player/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk());

        verify(playerService).savePlayer(player);
    }

    @Test
    public void testDebitTransaction() throws Exception {
        String username = "john";
        BigDecimal amount = new BigDecimal("50");

        mockMvc.perform(post("/player/{username}/debit", username)
                        .param("amount", amount.toString()))
                .andExpect(status().isOk());

        verify(playerService).debitTransaction(username, amount);
    }

    @Test
    public void testCreditTransaction() throws Exception {
        String username = "john";
        BigDecimal amount = new BigDecimal("50");

        mockMvc.perform(post("/player/{username}/credit", username)
                        .param("amount", amount.toString()))
                .andExpect(status().isOk());

        verify(playerService).creditTransaction(username, amount);
    }

    @Test
    public void testGetPlayerActions() throws Exception {
        String username = "john";
        Action action1 = new Action(1L, username, "action1", "detail1");
        Action action2 = new Action(2L, username, "action2", "detail2");
        List<Action> actions = Arrays.asList(action1, action2);

        when(playerService.getPlayerActions(username)).thenReturn(actions);

        mockMvc.perform(get("/player/{username}/actions", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].username").value(username))
                .andExpect(jsonPath("$.[0].actionName").value("action1"))
                .andExpect(jsonPath("$.[0].detail").value("detail1"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].username").value(username))
                .andExpect(jsonPath("$.[1].actionName").value("action2"))
                .andExpect(jsonPath("$.[1].detail").value("detail2"));

        verify(playerService).getPlayerActions(username);
    }
}