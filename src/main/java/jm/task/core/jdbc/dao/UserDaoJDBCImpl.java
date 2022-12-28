package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    public void createUsersTable() {

        try (PreparedStatement sqlCreate = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users " +
                "(id INT NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(45) NOT NULL," +
                "lastName VARCHAR(45) NOT NULL," +
                "age INT(3) NOT NULL, " +
                "PRIMARY KEY (id), UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE)")) {
            sqlCreate.execute();
        } catch (SQLException throwables) {
            System.out.println("Не получилось создать таблицу");
        }
    }

    public void dropUsersTable() {

        try (PreparedStatement sqlDrop = connection.prepareStatement("DROP TABLE IF EXISTS users")) {
            sqlDrop.execute();
        } catch (SQLException throwables) {
            System.out.println("Не получилось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement sqlSave = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?,?,?)")) {
            sqlSave.setString(1, name);
            sqlSave.setString(2, lastName);
            sqlSave.setInt(3, age);
            sqlSave.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            System.out.println("Не получилось добавить пользователя");
        }
    }

    public void removeUserById(long id) {

        try (PreparedStatement sqlRemove = connection.prepareStatement("DELETE FROM users where id = ?")) {
            sqlRemove.setLong(1, id);
            sqlRemove.execute();
            System.out.println("User с id " + id + " удален из базы данных");
        } catch (SQLException throwables) {
            System.out.println("Не получилось удалить пользователя по Id");
        }

    }

    public List<User> getAllUsers() {

        List<User> list = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User();

                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge((byte) resultSet.getInt(4));
                list.add(user);
            }
        } catch (SQLException throwables) {
            System.out.println("Не удалось получить всех пользователей");
        }
        return list;
    }

    public void cleanUsersTable() {

        try (PreparedStatement sqlClean = connection.prepareStatement("TRUNCATE TABLE users")) {
            sqlClean.execute();
        } catch (SQLException throwables) {
            System.out.println("Не получилось очистить таблицу");
        }
    }
}
