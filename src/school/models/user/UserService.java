package school.models.user;

import school.helper.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    public boolean loginUser(String username, String password) {
        boolean condition = false;
        Connection connection = DBConnection.getConnection();

        String sql = "SELECT * FROM users WHERE name=? AND password=?";
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            resultSet = ps.executeQuery();
           if (resultSet.next()) {
               condition = true;
           }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection,ps,resultSet);
        return condition;
    }

    public boolean changePassword(String currentPassword, String newPassword) {
        boolean condition = false;

        String sql = "UPDATE users SET password=? WHERE password=?";

        Connection connection = DBConnection.getConnection();
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, currentPassword);
            int result = ps.executeUpdate();
            if (result == 1) {
                condition = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DBConnection.closeConnection(connection, ps, null);

        return condition;
    }
}
