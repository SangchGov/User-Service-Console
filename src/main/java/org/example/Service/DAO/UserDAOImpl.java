package org.example.Service.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Entity.User;
import org.example.HibernateUntilApp;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    @Override
    public User create(User user) {
        Transaction tx = null;

        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Failed to create user: {}", user, e);
            throw e;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        } catch (Exception e) {
            logger.error("Failed to find user by id: {}", id, e);
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            logger.error("Failed to fetch all users", e);
            throw e;
        }
    }

    @Override
    public User update(User user) {
        Transaction tx = null;

        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Failed to update user: {}", user, e);
            throw e;
        }
    }

    @Override
    public boolean delete(Long id) {
        Transaction tx = null;

        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user == null) {
                return false;
            }
            session.remove(user);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Failed to delete user with id: {}", id, e);
            throw e;
        }
    }
}
