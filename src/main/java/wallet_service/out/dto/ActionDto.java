package wallet_service.out.dto;

import jakarta.validation.constraints.NotBlank;

public class ActionDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Action detail cannot be blank")
    private String actionDetail;
    @NotBlank(message = "Action performed cannot be blank")
    private String actionPerformed;

    public ActionDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public String getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed(String actionPerformed) {
        this.actionPerformed = actionPerformed;
    }
}
