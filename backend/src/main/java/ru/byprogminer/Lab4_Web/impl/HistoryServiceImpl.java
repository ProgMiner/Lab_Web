package ru.byprogminer.Lab4_Web.impl;

import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.history.QueryEntity;

import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class HistoryServiceImpl implements HistoryService {

    // TODO replace by repository
    private final Map<Long, Deque<QueryEntity>> queries = new HashMap<>();

    @Override
    public Deque<QueryEntity> getQueries(long userId) {
        final Deque<QueryEntity> queries = this.queries.get(userId);

        if (queries == null) {
            return new LinkedList<>();
        } else {
            return new LinkedList<>(queries);
        }
    }

    @Override
    public boolean addQuery(QueryEntity query) {
        if (query.getId() != null) {
            throw new IllegalArgumentException();
        }

        final Deque<QueryEntity> queries = this.queries.get(query.getUserId());

        if (queries == null) {
            this.queries.put(query.getUserId(), new LinkedList<>(Collections.singleton(new QueryEntity(1L,
                    query.getUserId(), query.getX(), query.getY(), query.getR(), query.getResult()))));
            return true;
        }

        final QueryEntity last = queries.getLast();
        if (last.getX().equals(query.getX()) && last.getY().equals(query.getY()) && last.getR().equals(query.getR())) {
            return false;
        }

        queries.addLast(new QueryEntity(queries.size() + 1L, query.getUserId(),
                query.getX(), query.getY(), query.getR(), query.getResult()));
        return true;
    }

    @Override
    public void clear(long userId) {
        queries.remove(userId);
    }
}
