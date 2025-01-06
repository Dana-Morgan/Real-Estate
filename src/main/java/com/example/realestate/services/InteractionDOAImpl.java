package com.example.realestate.services;

import com.example.realestate.models.Customer;
import com.example.realestate.models.Interaction;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class InteractionDOAImpl implements InteractionDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public InteractionDOAImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

    @Override
    public void save(Interaction interaction) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(interaction);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Interaction interaction) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(interaction);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Interaction interaction) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(interaction);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Interaction> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Interaction", Interaction.class).list();
        }
    }

    @Override
    public Interaction getById(int interactionId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Interaction.class, interactionId);
        }
    }

    @Override
    public boolean isCustomerExist(int customerID) {
        try (Session session = sessionFactory.openSession()) {
            Query<Customer> query = session.createQuery("FROM Customer WHERE customerId = :customerID", Customer.class);
            query.setParameter("customerID", customerID);
            return query.uniqueResult() != null;
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
}
