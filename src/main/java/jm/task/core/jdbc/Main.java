package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Ксения", "Федорова", (byte) 18);
        userDao.saveUser("Иван", "Узлов", (byte) 26);
        userDao.saveUser("Артем", "Пирунов", (byte) 30);
        userDao.saveUser("Карина", "Букина", (byte) 17);
        userDao.removeUserById(2L);
        List<User> userList = userDao.getAllUsers();
        for (User u : userList) {
            System.out.println(u);
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();


    }
}
