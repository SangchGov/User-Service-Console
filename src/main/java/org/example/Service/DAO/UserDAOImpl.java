package org.example.Service.DAO;

import org.example.Entity.User;
import org.example.HibernateUntilApp;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO{

    @Override
    public User create(User user) {
        Transaction tx = null;

        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
            return user;
        }
        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }


    @Override
    public Optional<User> findById(Long id){
        try(Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }


    @Override
    public User update(User user){

        Transaction tx = null;

        try (Session session = HibernateUntilApp.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(user);
            tx.commit();
            return user;
        }

        catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    @Override
    public boolean delete(Long id){

        Transaction tx = null;

        try(Session session = HibernateUntilApp.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            User user = session.get(User.class, id );
            if(user == null) return false;
            session.remove(user);
            tx.commit();
            return true;
        }


    }


}
