package wallet_service.in.model;

/**
 * Класс, представляющий действие пользователя.
 *
 * @author Олег Тодор
 */
public class Action {
    private String username; // Имя пользователя
    private String action; // Действие
    private String detail; // Детали действия

    /**
     * Конструктор класса Action.
     *
     * @param username Имя пользователя
     * @param action   Действие
     * @param detail   Детали действия
     */
    public Action(String username, String action, String detail) {
        this.username = username;
        this.action = action;
        this.detail = detail;
    }

    /**
     * Получить имя пользователя.
     *
     * @return Имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Получить действие.
     *
     * @return Действие
     */
    public String getAction() {
        return action;
    }

    /**
     * Получить детали действия.
     *
     * @return Детали действия
     */
    public String getDetail() {
        return detail;
    }
}