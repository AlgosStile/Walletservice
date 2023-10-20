package wallet_service.in.model;


/**
 * Класс Action представляет действие, связанное с пользователем.
 * Автор: Олег Тодор
 * Версия: 2.0.0
 */
public class Action {
    private String username;
    private String action;
    private String detail;

    /**
     * Конструктор класса Action с указанными параметрами.
     *
     * @param username имя пользователя
     * @param action   действие
     * @param detail   детали действия
     */
    public Action(String username, String action, String detail) {
        this.username = username;
        this.action = action;
        this.detail = detail;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает действие.
     *
     * @return действие
     */
    public String getAction() {
        return action;
    }

    /**
     * Возвращает детали действия.
     *
     * @return детали действия
     */
    public String getDetail() {
        return detail;
    }
}
