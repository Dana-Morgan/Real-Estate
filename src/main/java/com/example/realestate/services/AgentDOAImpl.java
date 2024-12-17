package com.example.realestate.services;

import com.example.realestate.models.Agent;
import com.example.realestate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
    public List<Agent> getAll(Agent agent) {
        return List.of();
    }

    @Override
    public Agent getByEmail(String Email) {
        return null;
    }
}
