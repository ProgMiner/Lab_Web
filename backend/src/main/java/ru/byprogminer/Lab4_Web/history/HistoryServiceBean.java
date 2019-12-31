package ru.byprogminer.Lab4_Web.history;

import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;
import java.util.ArrayDeque;
import java.util.stream.Collectors;

@Stateless
public class HistoryServiceBean implements HistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ArrayDeque<QueryEntity> getQueries(UserEntity user) {
        return entityManager.createNamedQuery("history.findByUser", QueryEntity.class).getResultStream()
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    @Override
    public ArrayDeque<QueryEntity> getQueries(UserEntity user, long offset, long count) {
        return entityManager.createNamedQuery("history.findByUser", QueryEntity.class)
                .setFirstResult((int) offset).setMaxResults((int) count).getResultStream()
                .collect(Collectors.toCollection(ArrayDeque::new));
    }

    @Override
    public boolean addQuery(@NotNull QueryEntity query) {
        if (query.getId() != null) {
            query = new QueryEntity(null, query.getUser(), query.getX(), query.getY(), query.getR(), query.getResult());
        }

        try {
            entityManager.persist(query);
            entityManager.flush();
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean clear(UserEntity user) {
        try {
            return entityManager.createNamedQuery("history.deleteByUser")
                    .setParameter("user", user).executeUpdate() < 1;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }
    }
}
