package jm.task.core.jdbc.util;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/username";
    private static final String NAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String DRIVER = "com.mysql.cj.jdbc.driver";
    private static SessionFactory sessionFactory;

    public static Connection getConn() {
        try {
            Connection connection = DriverManager.getConnection(URL, NAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            System.out.println("NOT Connect");
        }
        return null;
    }

    public static SessionFactory getSessionFactor() {
        if (sessionFactory == null) {
            try {
            Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DRIVER);
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/username?useSSL=false");
                settings.put(Environment.USER, NAME);
                settings.put(Environment.PASS, PASSWORD);

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
