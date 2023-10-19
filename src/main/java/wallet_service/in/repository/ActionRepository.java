package wallet_service.in.repository;


import wallet_service.in.config.DBConnection;
import wallet_service.in.model.Action;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActionRepository {
    private static final String INSERT_SQL = "INSERT INTO wallet.actions(username, action, detail) VALUES (?, ?, ?)";

    private final PreparedStatement insertStatement;

    public ActionRepository() throws SQLException {
        insertStatement = DBConnection.getInstance().getConnection().prepareStatement(INSERT_SQL);
    }

    public void addAction(Action action) throws SQLException {
        insertStatement.setString(1, action.getUsername());
        insertStatement.setString(2, action.getAction());
        insertStatement.setString(3, action.getDetail());

        insertStatement.executeUpdate();
    }


}
