package wallet_service.out.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "actions")
@NoArgsConstructor
public class Action  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "action")
    private String action;
    @Column(name = "detail")
    private String detail;
    public Action(int id, String username, String action, String detail) {
        this.id = id;
        this.username = username;
        this.action = action;
        this.detail = detail;
    }

}
