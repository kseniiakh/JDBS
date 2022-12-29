package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/pp1.1.4";
    private static final String USER = "root";
    private static final String PASSWORD = "Crocodile1357.";

    private static Connection connection;

    public static Connection getConnection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                return connection;
            } catch (SQLException e) {
                System.out.println("Соединение не установлено");
            }
        }
        return connection;
    }

}
