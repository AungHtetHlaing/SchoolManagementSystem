package school.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    private DBConnection() {
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(getClass().getResource("/school/assets/resources/db.properties").getPath()));
            Class.forName(properties.getProperty("jdbc_driver"));
            connection = DriverManager.getConnection(
                    properties.getProperty("jdbc_url"),
                    properties.getProperty("jdbc_user"),
                    properties.getProperty("jdbc_pass"));
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            new DBConnection();
        }
        return connection;
    }

    public static void closeConnection(Connection con, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (con != null) {
            try {
                con.close();
                connection = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
