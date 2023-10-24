package wallet_service.in.model;


/**
 * Представляет действие, связанное с кошельком.
 *
 * Этот класс представляет информацию о действии, выполненном в кошельке и содержит имя пользователя,
 * выполнившего действие, описание действия и детали действия.
 *
 * @author Олег Тодор
 */
public class Action {

    private String username; // Имя пользователя
    private String action; // Действие
    private String detail; // Детали действия

    /**
     * Создает экземпляр класса Action с указанными параметрами.
     *
     * @param username Имя пользователя, выполнившего действие.
     * @param action   Описание действия.
     * @param detail   Детали действия.
     */
    public Action(String username, String action, String detail) {
        this.username = username;
        this.action = action;
        this.detail = detail;
    }

    /**
     * Возвращает имя пользователя, выполнившего действие.
     *
     * @return Имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Возвращает описание действия.
     *
     * @return Описание действия.
     */
    public String getAction() {
        return action;
    }

    /**
     * Возвращает детали действия.
     *
     * @return Детали действия.
     */
    public String getDetail() {
        return detail;
    }
}