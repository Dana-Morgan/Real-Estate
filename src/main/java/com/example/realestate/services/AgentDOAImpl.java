package com.example.realestate.services;

import com.example.realestate.models.Agent;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;

public class AgentDOAImpl implements AgentDOA{

    HibernateUtil hibernateUtil;
    SessionFactory sessionFactory;

    /*
    public EmployeeDOAImp(){
        hibernateUtil =  HibernateUtil.getInstance();
        sessionFactory = (SessionFactory) hibernateUtil.getSessionFactory();
    }
     */
    public AgentDOAImpl() {
        hibernateUtil = HibernateUtil.getInstance();
        sessionFactory = hibernateUtil.getSessionFactory();
    }

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



        @Override
        public Agent login(String email, String password) {
            SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
            Session session = sessionFactory.openSession();
            Agent agent = null;

            try {
                String hql = "FROM Agent WHERE Email = :email AND Password = :password";
                Query query = session.createQuery(hql);
                query.setParameter("email", email);
                query.setParameter("password", password);
                agent = (Agent) ((org.hibernate.query.Query<?>) query).uniqueResult();
            } catch (Exception e) {
                e.printStackTrace(); // Corrected here
            } finally {
                session.close();
            }

            return agent; // Returns null if no agent is found with given email and password
        }
    }
