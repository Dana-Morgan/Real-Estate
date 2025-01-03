package com.example.realestate.services;

import com.example.realestate.models.Admin;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;

public class AdminDAOImpl implements AdminDAO {

    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;


    public AdminDAOImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }
    /*
       @Override
       public void save(Agent agent) {
           SessionFactory sessionFactory =  hibernateUtil.getInstance().getSessionFactory();
           Session session = sessionFactory.openSession();
           session.beginTransaction();
           session.save(agent);
           session.getTransaction().commit();
           sessionFactory.openSession().close();
       }



           @Override
           public void update(Agent agent) {

           }

           @Override
           public void delete(Agent agent) {

           }



    @Override
    public List<Agent> getAll( ) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("SELECT u from Agent u").list();
    }

    @Override
    public Agent getByEmail(String Email) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.get(Agent.class, Email);
    }


     */


    @Override
    public Admin login(String email, String password) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        Admin admin = null;

        try {
            // Check if the email and password match Admin
            String hqlAdmin = "FROM Admin WHERE email = :email AND password = :password";
            Query queryAdmin = session.createQuery(hqlAdmin);
            queryAdmin.setParameter("email", email);
            queryAdmin.setParameter("password", password);
            admin = (Admin) ((org.hibernate.query.Query<?>) queryAdmin).uniqueResult();

            // If no Admin found, try checking if it's an Agent
            if (admin == null) {
                String hqlAgent = "FROM Agent WHERE Email = :email AND Password = :password";
                Query queryAgent = session.createQuery(hqlAgent);
                queryAgent.setParameter("email", email);
                queryAgent.setParameter("password", password);
                admin = (Admin) ((org.hibernate.query.Query<?>) queryAgent).uniqueResult();  // You can cast it to Admin or create a separate Agent object
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return admin; // Returns null if no matching Admin or Agent is found
    }

}
