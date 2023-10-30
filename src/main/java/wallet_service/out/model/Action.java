package wallet_service.out.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

/**
 * Класс представляет сущность "Действие".
 * Содержит информацию о действии пользователя.
 */
@Getter
@Entity
@Table(name = "actions")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "action")
    private String action;
    @Column(name = "detail")
    private String detail;

    /**
     * Конструктор класса Action.
     *
     * @param id       уникальный идентификатор действия
     * @param username имя пользователя, выполнившего действие
     * @param action   описание действия
     * @param detail   детали действия
     */
    public Action(Long id, String username, String action, String detail) {
        this.id = id;
        this.username = username;
        this.action = action;
        this.detail = detail;
    }


}
