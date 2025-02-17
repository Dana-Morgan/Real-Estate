package com.example.realestate.utils;

import com.example.realestate.models.Agreement;
import com.example.realestate.models.Interaction;
import com.example.realestate.models.Customer;
import com.example.realestate.models.Property;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static HibernateUtil instance = null;
    public static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;
    private HibernateUtil(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Agent.class);
        configuration.addAnnotatedClass(Admin.class);
        configuration.addAnnotatedClass(Interaction.class);
        configuration.addAnnotatedClass(Agreement.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Property.class);

        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static HibernateUtil getInstance(){
        if(instance == null){
            instance  = new HibernateUtil();
        }
        return instance;
    }

    public synchronized static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
