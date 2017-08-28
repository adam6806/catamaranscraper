package com.github.adam6806.catamaranscraper.database.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public abstract class AbstractDAO<T> {

    private Session session;
    private Transaction transaction;

    private static SessionFactory getSessionFactory() {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }

    public Session openSession() {
        session = getSessionFactory().openSession();
        return session;
    }

    public Session openSessionWithTransaction() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void closeSessionWithTransaction() {
        transaction.commit();
        session.close();
    }

    protected Session getSession() {
        return session;
    }

    protected void setSession(Session session) {
        this.session = session;
    }

    protected Transaction getTransaction() {
        return transaction;
    }

    protected void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    protected abstract void save(T entity);

    protected abstract void update(T entity);

    protected abstract void delete(T entity);

    protected abstract void deleteAll();

    protected abstract T findById(int id);

    protected abstract List<T> findAll();
}
