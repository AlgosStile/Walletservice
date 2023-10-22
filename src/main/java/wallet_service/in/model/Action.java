package wallet_service.in.model;


public class Action {
    private String username; // Имя пользователя
    private String action; // Действие
    private String detail; // Детали действия


    public Action(String username, String action, String detail) {
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