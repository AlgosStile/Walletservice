package main.java.wallet_service.out.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс Action представляет действие, связанное с пользователем.
 * Он содержит информацию об идентификаторе действия, имени пользователя,
 * самом действии и его деталях.
 *
 * @author Олег Тодор
 */
@Setter
@Getter
@Entity
@Table(name = "actions")
@NoArgsConstructor
public class Action {
    /**
     * Идентификатор действия.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Имя пользователя, связанного с действием.
     */
    @Column(name = "username")
    private String username;

    /**
     * Описание действия.
     */
    @Column(name = "action")
    private String action;
    /**
     * Детали действия.
     */
    @Column(name = "detail")
    private String detail;

    /**
     * Конструктор класса Action.
     *
     * @param id       идентификатор действия
     * @param username имя пользователя
     * @param action   описание действия
     * @param detail   детали действия
     */
    public Action(int id, String username, String action, String detail) {
        this.id = id;
        this.username = username;
        this.action = action;
        this.detail = detail;
    }
}
