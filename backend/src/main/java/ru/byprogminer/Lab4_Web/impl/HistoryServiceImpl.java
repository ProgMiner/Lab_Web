package ru.byprogminer.Lab4_Web.impl;

import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.history.QueryEntity;

import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

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
    public Deque<QueryEntity> getQueries(long userId, long offset, long count) {
        final Deque<QueryEntity> queries = this.queries.get(userId);

        if (queries == null) {
            return new LinkedList<>();
        } else {
            return queries.parallelStream().skip(offset).limit(count)
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }

    @Override
    public boolean addQuery(@NotNull QueryEntity query) {
        if (query.getId() != null) {
            query = new QueryEntity(null, query.getUserId(), query.getX(), query.getY(), query.getR(), query.getResult());
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
