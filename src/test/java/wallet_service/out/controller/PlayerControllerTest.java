package wallet_service.out.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wallet_service.out.model.Action;
import wallet_service.out.model.Player;
import wallet_service.out.service.PlayerServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PlayerServiceImpl playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new PlayerController(playerService)).build();
    }


    @Test
    @DisplayName("testSavePlayer")
    public void testSavePlayer() throws Exception {
        mockMvc.perform(post("/player/").contentType(MediaType.APPLICATION_JSON).content("{\"username\":\"username\",\"name\":\"John Doe\",\"balance\":100.00}")).andExpect(status().isOk());

        verify(playerService, times(1)).savePlayer(any(Player.class));
    }

    @Test
    @DisplayName("testDebitTransaction")
    public void testDebitTransaction() throws Exception {
        String username = "john";
        BigDecimal amount = new BigDecimal("50");

        mockMvc.perform(post("/player/{username}/debit", username).param("amount", amount.toString())).andExpect(status().isOk());

        verify(playerService).debitTransaction(username, amount);
    }

    @Test
    @DisplayName("testCreditTransaction")
    public void testCreditTransaction() throws Exception {
        String username = "john";
        BigDecimal amount = new BigDecimal("50");

        mockMvc.perform(post("/player/{username}/credit", username).param("amount", amount.toString())).andExpect(status().isOk());

        verify(playerService).creditTransaction(username, amount);
    }

    @Test
    @DisplayName("testGetPlayerActions")
    public void testGetPlayerActions() throws Exception {
        String username = "john";
        List<Action> actions = new ArrayList<>();
        actions.add(new Action(122222, "username", "", "some_value"));
        actions.add(new Action(6225622, "username", "", "some_value"));

        when(playerService.getPlayerActions(username)).thenReturn(actions);

        mockMvc.perform(get("/players/1/actions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").exists())
                .andExpect(jsonPath("$[0].type").value("some_value"))
                .andExpect(jsonPath("$[0].amount").exists())
                .andExpect(jsonPath("$[0].amount").exists());

        verify(playerService).getPlayerActions(username);
    }

}

