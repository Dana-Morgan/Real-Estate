package com.example.realestate.services;

import com.example.realestate.models.Agreement;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.realestate.models.Customer;
import com.example.realestate.models.Property;
import org.hibernate.query.Query;
import java.util.List;

public class AgreementDAOImpl implements AgreementDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public AgreementDAOImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Agreement agreement) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(agreement);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Agreement agreement) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(agreement);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Agreement agreement) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(agreement);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Agreement> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Agreement", Agreement.class).list();
        }
    }

    @Override
    public Agreement getById(int displayID) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Agreement.class, displayID);
        }
    }

    @Override
    public boolean isCustomerExists(int customerId) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.customerId = :customerId", Long.class)
                    .setParameter("customerId", customerId)
                    .uniqueResult();
            return count > 0;
        }
    }

    @Override
    public boolean isPropertyExists(int propertyId) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("SELECT COUNT(p) FROM Property p WHERE p.id = :propertyId", Long.class)
                    .setParameter("propertyId", propertyId)
                    .uniqueResult();
            return count > 0;
        }
    }

    @Override
    public Customer findCustomerById(int customerID) {
        try (Session session = sessionFactory.openSession()) {
            Query<Customer> query = session.createQuery("FROM Customer WHERE customerId = :customerID", Customer.class);
            query.setParameter("customerID", customerID);
            return query.uniqueResult();
        }
    }

    @Override
    public Property findPropertyById(int propertyID) {
        try (Session session = sessionFactory.openSession()) {
            Query<Property> query = session.createQuery("FROM Property WHERE id = :propertyID", Property.class);
            query.setParameter("propertyID", propertyID);
            return query.uniqueResult();
        }
    }
}