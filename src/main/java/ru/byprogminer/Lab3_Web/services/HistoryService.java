package ru.byprogminer.Lab3_Web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.byprogminer.Lab3_Web.entities.QueryEntity;

import java.util.List;

public class HistoryService {

    private final SessionFactory sessionFactory;

    public HistoryService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<QueryEntity> getHistory() {
        final Session session = sessionFactory.openSession();

        session.beginTransaction();
        final List<QueryEntity> ret = session.createQuery("from history order by id", QueryEntity.class).list();

        session.getTransaction().commit();
        session.close();

        return ret;
    }

    public List<QueryEntity> getHistoryDesc(int offset, int size) {
        final Session session = sessionFactory.openSession();

        session.beginTransaction();
        final List<QueryEntity> ret = session.createQuery("from history order by id desc", QueryEntity.class)
                .setFirstResult(offset).setMaxResults(size).list();

        session.getTransaction().commit();
        session.close();

        return ret;
    }

    public long getHistoryLength() {
        final Session session = sessionFactory.openSession();

        session.beginTransaction();
        final long ret = session.createQuery("select count(id) from history", Long.class).getSingleResult();

        session.getTransaction().commit();
        session.close();

        return ret;
    }

    public void addQuery(QueryEntity entity) {
        final Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.persist(entity);

        session.getTransaction().commit();
        session.close();
    }
}
