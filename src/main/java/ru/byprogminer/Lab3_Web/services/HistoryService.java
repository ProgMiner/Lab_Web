package ru.byprogminer.Lab3_Web.services;

import ru.byprogminer.Lab3_Web.entities.QueryEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class HistoryService {

    private static final String PERSISTENCE_UNIT_NAME = "default";

    private final EntityManagerFactory entityManagerFactory;

    public HistoryService() {
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public List<QueryEntity> getHistory() {
        final EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();
        final List<QueryEntity> ret = manager.createQuery("from history", QueryEntity.class).getResultList();

        manager.getTransaction().commit();
        manager.close();

        return ret;
    }

    public QueryEntity addQuery(QueryEntity entity) {
        final EntityManager manager = entityManagerFactory.createEntityManager();

        manager.getTransaction().begin();
        manager.persist(entity);

        manager.getTransaction().commit();
        manager.close();

        return entity;
    }
}
