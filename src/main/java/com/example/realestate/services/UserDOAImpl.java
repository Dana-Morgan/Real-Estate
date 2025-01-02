package com.example.realestate.services;

import com.example.realestate.models.Agent;
import com.example.realestate.models.User;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class UserDOAImpl implements  UserDOA {

    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;

    public UserDOAImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(User user) {
        SessionFactory sessionFactory = hibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        sessionFactory.openSession().close();
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAll() {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            // إزالة الشرط "WHERE"
            String hql = "FROM User"; // استعلام يجلب كل السجلات
            return session.createQuery(hql, User.class).getResultList();
        } finally {
            session.close();
        }
    }

    @Override
    public User getByEmail(String Email) {
        return null;
    }
/*
    @Override
    public User login(String email, String password) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = null;

        try {
            String hql = "FROM User WHERE email = :email AND password = :password";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", password);
            user = (User) ((org.hibernate.query.Query<?>) query).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user; // Returns null if no agent is found with given email and password
    }
*/
@Override
public User login(String email, String password) {
    SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    Session session = sessionFactory.openSession();
    User user = null;

    try {
        String hql = "FROM User WHERE email = :email AND password = :password";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("password", password);
        user = (User) ((org.hibernate.query.Query<?>) query).uniqueResult();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        session.close();
    }

    return user; // Returns null if no user is found with the given credentials
}

    public boolean emailExists(String email) {
        Session session = sessionFactory.openSession();
        boolean exists = false;

        try {
            String hql = "SELECT COUNT(a) FROM User a WHERE a.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            Long count = (Long) query.getSingleResult();
            exists = count > 0; // If count > 0, email exists
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return exists;
    }
    @Override
    public boolean updatePassword(String email, String newPassword) {
        return false;
    }

}