package wallet_service.out.dto;

public class ActionDto {
    private String username;
    private String action;
    private String detail;

    public ActionDto(String username, String action, String detail) {
        this.username = username;
        this.action = action;
        this.detail = detail;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public String getDetail() {
        return detail;
    }
}