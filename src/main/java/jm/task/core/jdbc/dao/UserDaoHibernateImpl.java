package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.UtilHibernate.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    public UserDaoHibernateImpl() {

        sessionFactory = UtilHibernate.getSessionFactory();
    }


    @Override
    public void createUsersTable() {

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, " +
                    "lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Hib. Ошибка создания таблицы");
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if exists users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Hib. Ошибка удаления таблицы");
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Hib. Ошибка добавления пользователя в таблицу");
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Hib. Ошибка удаления пользователя из таблицы");
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {

        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        List<User> user = session.createQuery("from User").list();
        transaction.commit();
        session.close();

        return user;
    }


    @Override
    public void cleanUsersTable() {

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            System.out.println("Hib. Ошибка очистки таблицы");
        } finally {
            session.close();
        }

    }
}
