package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactor;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactor().openSession()) {
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS User " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactor().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactor().getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            user.setId(null);
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactor().getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactor().getCurrentSession()) {
            session.beginTransaction();
            List<User> list = session.createQuery("from User").list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactor().getCurrentSession();) {
            session.beginTransaction();
            session.createQuery("delete User ").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
