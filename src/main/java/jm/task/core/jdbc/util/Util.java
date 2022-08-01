package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost/username";
    private static final String NAME = "admin";
    private static final String PASSWORD = "admin";

    public static Connection getConn() {
        try {
            Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.out.println("NOT Connect");
        }
        return null;
    }

}
