package wallet_service.out.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO класс для передачи действий, выполненных пользователями.
 * Содержит информацию о том, кто выполнил действие, какое действие было выполнено и детали этого действия.
 *
 * @author Олег Тодор
 */
public class ActionDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Action detail cannot be blank")
    private String actionDetail;

    @NotBlank(message = "Action performed cannot be blank")
    private String actionPerformed;

    /**
     * Конструктор по умолчанию для класса ActionDto.
     */
    public ActionDto() {
    }

    /**
     * Получить имя пользователя, производившего действие.
     *
     * @return Имя пользователя, производившего действие
     */
    public String getUsername() {
        return username;
    }

    /**
     * Установить имя пользователя, производившего действие.
     *
     * @param username имя пользователя, производившего действие
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Получить детали действия.
     *
     * @return Детали произведенного действия
     */
    public String getActionDetail() {
        return actionDetail;
    }

    /**
     * Установить детали действия.
     *
     * @param actionDetail детали произведенного действия
     */
    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    /**
     * Получить информацию о выполненном дейтвии.
     *
     * @return Информация о выполненном дейтвии
     */
    public String getActionPerformed() {
        return actionPerformed;
    }

    /**
     * Установить информацию о выполненном дейтвии.
     *
     * @param actionPerformed информация о выполненном дейтвии
     */
    public void setActionPerformed(String actionPerformed) {
        this.actionPerformed = actionPerformed;
    }
}
