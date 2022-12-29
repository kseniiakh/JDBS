package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class UtilHibernate {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration con = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                properties.put(Environment.URL, "jdbc:mysql://localhost:3306/pp1.1.4");
                properties.put(Environment.USER, "root");
                properties.put(Environment.PASS, "Crocodile1357.");
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                con.setProperties(properties);
                con.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(con.getProperties()).build();

                sessionFactory = con.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.out.println("Ошибка в создании Hibernate соединения");
            }
        }
        return sessionFactory;
    }
}
