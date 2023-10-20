package wallet_service.in.repository;


import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Action;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * ActionRepository является классом, предоставляющим методы для взаимодействия с таблицей actions базы данных.
 * Методы данного класса позволяют добавлять новые действия пользователя в систему.
 *
 * @author Олег Тодор
 * @since 2.0.0
 */

public class ActionRepository {

    /**
     * SQL-запрос для вставки нового действия в таблицу actions.
     */
    private static final String INSERT_SQL = "INSERT INTO wallet.actions(username, action, detail) VALUES (?, ?, ?)";

    /**
     * Подготовленный SQL-запрос для вставки нового действия в таблицу actions.
     */
    private final PreparedStatement insertStatement;

    /**
     * Конструктор класса ActionRepository.
     * В процессе создания объекта класса подготавливается SQL-запрос для дальнейшего использования.
     *
     * @throws SQLException если во время создания объекта произошла ошибка доступа к базе данных.
     */
    public ActionRepository() throws SQLException {
        insertStatement = DBConnection.getInstance().getConnection().prepareStatement(INSERT_SQL);
    }

    /**
     * Метод для добавления нового действия в базу данных.
     *
     * @param action действие пользователя, которое необходимо добавить в базу данных.
     *
     * @throws SQLException если при добавлении действия произошла ошибка доступа к базе данных.
     */
    public void addAction(Action action) throws SQLException {
        insertStatement.setString(1, action.getUsername());
        insertStatement.setString(2, action.getAction());
        insertStatement.setString(3, action.getDetail());

        insertStatement.executeUpdate();
    }
}
