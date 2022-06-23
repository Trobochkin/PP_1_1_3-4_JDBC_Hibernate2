package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS `newbase`.`User` (
                  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                  `name` VARCHAR(45) NOT NULL,
                  `lastName` VARCHAR(45) NOT NULL,
                  `age` INT NOT NULL)""";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS `newbase`.`User`";
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        Session session = Util.getSessionFactory().openSession();

        try (session) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = Util.getSessionFactory().openSession();

        try (session) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null)
                session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        Session session = Util.getSessionFactory().openSession();

        try (session) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User")
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = Util.getSessionFactory().openSession();

        try (session) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            e.printStackTrace();
        }
    }
}
