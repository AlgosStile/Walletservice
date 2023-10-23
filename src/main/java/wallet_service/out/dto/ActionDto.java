package wallet_service.out.dto;

import jakarta.validation.constraints.NotBlank;

public class ActionDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Action cannot be blank")
    private String action;

    @NotBlank(message = "Detail cannot be blank")
    private String detail;

    public ActionDto() {
    }

    public ActionDto(String username, String action, String detail) {
        this.username = username;
        this.action = action;
        this.detail = detail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}