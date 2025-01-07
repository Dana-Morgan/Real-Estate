package com.example.realestate.services;

import com.example.realestate.models.Customer;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class CustomerDAOimp implements CustomerDAO {
    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public CustomerDAOimp(){
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error saving/updating Customer", e);
        } finally {
            session.close();
        }
    }


    @Override
    public List<Customer> getAllCustomers() {
        Session session = sessionFactory.openSession();
        try {
            Query<Customer> query = session.createQuery("FROM Customer", Customer.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving customers", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error deleting customer", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating customer", e);
        } finally {
            session.close();
        }
    }
    @Override
    public long getCustomerCount() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT COUNT(a) FROM Customer a", Long.class).uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}