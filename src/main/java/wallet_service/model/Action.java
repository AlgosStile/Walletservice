
package wallet_service.model;

public class Action {
    private String username;
    private String action;
    private String detail;

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


