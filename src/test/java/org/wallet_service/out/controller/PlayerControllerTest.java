package test.java.org.wallet_service.out.controller;


import main.java.org.wallet_service.WalletServiceApplication;
import main.java.org.wallet_service.out.controller.PlayerController;
import main.java.org.wallet_service.out.model.Action;
import main.java.org.wallet_service.out.model.Player;
import main.java.org.wallet_service.out.service.PlayerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PlayerController.class)
@ContextConfiguration(classes = WalletServiceApplication.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PlayerServiceImpl playerService;

    @Test
    @DisplayName("Get player")
    public void getPlayerTest() throws Exception {
        Player player = new Player();
        player.setUsername("test");

        given(playerService.findByUsername("test")).willReturn(player);

        mvc.perform(get("/{username}", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("test"));
    }


    @Test
    @DisplayName("Save player")
    public void savePlayerTest() throws Exception {
        Player player = new Player();
        player.setUsername("test");

        mvc.perform(post("/")
                        .content("{\"username\": \"test\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Debit transaction")
    public void debitTransactionTest() throws Exception {
        mvc.perform(post("/{username}/debit", "test")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Credit transaction")
    public void creditTransactionTest() throws Exception {
        mvc.perform(post("/{username}/credit", "test")
                        .param("amount", "100")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get player actions")
    public void getPlayerActionsTest() throws Exception {
        Action action = new Action();
        action.setUsername("test");

        List<Action> actions = Collections.singletonList(action);

        given(playerService.getPlayerActions("test")).willReturn(actions);

        mvc.perform(get("/{username}/actions", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("test"));
    }

}
