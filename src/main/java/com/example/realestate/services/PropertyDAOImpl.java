package com.example.realestate.services;

import com.example.realestate.models.Property;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PropertyDAOImpl implements PropertyDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public PropertyDAOImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }


    @Override
    public List<Property> getAllProperties() {
        Session session = sessionFactory.openSession();
        try {
            Query<Property> query = session.createQuery("from Property");
            return query.list();
        } catch (Exception e) {
            System.err.println("Error retrieving properties: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error retrieving properties", e);
        }
    }

    @Override
    public void deleteProperty(Property property) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Property existingProperty = session.get(Property.class, property.getId());
            if (existingProperty != null) {
                session.delete(existingProperty);
                transaction.commit();
                System.out.println("Property deleted successfully: " + property.getId());
            } else {
                System.out.println("Property not found: " + property.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting property", e);
        }
    }


}





