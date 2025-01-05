package com.example.realestate.utils;

import com.example.realestate.models.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.Session;


public class HibernateUtil {

    private static volatile HibernateUtil instance = null;
    private static volatile SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    private HibernateUtil(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Agent.class);
        configuration.addAnnotatedClass(Admin.class);
        configuration.addAnnotatedClass(Interaction.class);
        configuration.addAnnotatedClass(Agreement.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Property.class);
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateUtil getInstance(){
        if(instance == null) {
            synchronized (HibernateUtil.class) {
                if (instance == null) {
                    instance = new HibernateUtil();
                }
            }
        }
        return instance;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static boolean isConnected() {
        try (Session session = sessionFactory.openSession()) {
            return session.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}