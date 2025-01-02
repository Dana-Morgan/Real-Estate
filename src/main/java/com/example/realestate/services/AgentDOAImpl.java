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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(agent);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Agent agent) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(agent);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Agent> getAll( ) {
        SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        return session.createQuery("SELECT u from Agent u").list();
    }

    @Override
    public Agent getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Agent WHERE Email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            List<Agent> results = query.getResultList();
            if (results.isEmpty()) {
                return null;
            }
            return results.get(0);
        }
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        try (Session session = sessionFactory.openSession()) {

            String emailCheckQuery = "SELECT COUNT(*) FROM Agent WHERE Email = :email";
            Query query = session.createQuery(emailCheckQuery);
            query.setParameter("email", email);
            long count = (long) query.getSingleResult();

            if (count == 0) {
                System.out.println("No agent found with the provided email: " + email);
                return false;
            }
            session.beginTransaction();
            String hql = "UPDATE Agent SET Password = :password WHERE Email = :email";
            Query updateQuery = session.createQuery(hql);
            updateQuery.setParameter("password", newPassword);
            updateQuery.setParameter("email", email);
            int result = updateQuery.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Password update result: " + result); // Debugging
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExists(String email) {
        Session session = sessionFactory.openSession();
        boolean exists = false;

        try {
            String hql = "SELECT COUNT(a) FROM Agent a WHERE a.Email = :email";
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
                e.printStackTrace();
            } finally {
                session.close();
            }

            return agent; // Returns null if no agent is found with given email and password
        }
    }
