package com.example.realestate.services;
import java.util.HashMap;

import com.example.realestate.models.Property;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Map;
public class PropertyDAOImpl implements PropertyDAO {

    private HibernateUtil hibernateUtil;
    private SessionFactory sessionFactory;

    public PropertyDAOImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    @Override
    public List<Property> getRandomProperties(int limit) {
        try (Session session = sessionFactory.openSession()) {
            Query<Property> query = session.createQuery("FROM Property ORDER BY RAND()", Property.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error retrieving random properties: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error retrieving random properties", e);
        }
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
    @Override
    public List<Property> searchProperties(Map<String, Object> filters) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Property WHERE 1=1");
            Map<String, Object> parameters = new HashMap<>();

            filters.forEach((key, value) -> {
                if (value != null && !value.toString().trim().isEmpty()) {
                    switch (key) {
                        case "LOWER(propertyType)":
                            hql.append(" AND LOWER(propertyType) LIKE :propertyType");
                            parameters.put("propertyType", "%" + value.toString() + "%");
                            break;
                        case "numberOfRooms":
                            hql.append(" AND numberOfRooms = :numberOfRooms");
                            parameters.put("numberOfRooms", value);
                            break;
                        case "LOWER(area)":
                            hql.append(" AND LOWER(area) LIKE :area");
                            parameters.put("area", "%" + value.toString() + "%");
                            break;
                        case "price":
                            hql.append(" AND CAST(price AS double) = :price");
                            parameters.put("price", value);
                            break;
                        case "LOWER(location)":
                            hql.append(" AND LOWER(location) LIKE :location");
                            parameters.put("location", "%" + value.toString() + "%");
                            break;
                        case "LOWER(status)":
                            hql.append(" AND LOWER(status) LIKE :status");
                            parameters.put("status", "%" + value.toString() + "%");
                            break;
                        case "date":
                            hql.append(" AND DATE(date) = DATE(:date)");
                            parameters.put("date", value);
                            break;
                    }
                }
            });

            Query<Property> query = session.createQuery(hql.toString(), Property.class);
            parameters.forEach(query::setParameter);

            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error searching properties: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error searching properties", e);
        }
    }
    public long getPropertyCount() {
        long count = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(p) FROM Property p", Long.class);
            count = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}