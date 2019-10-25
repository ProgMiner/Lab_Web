package ru.byprogminer.Lab3_Web.ee;

import ru.byprogminer.Lab3_Web.Query;

import java.util.*;

public class HistoryBean {

    private final Deque<Query> queries;

    public HistoryBean() {
        queries = new LinkedList<>();
    }

    public HistoryBean(Deque<Query> queries) {
        this.queries = queries;
    }

    void updateHistory(Query query) {
        if (query.getResult() == null) {
            return;
        }

        final Query latest = queries.peekFirst();

        if (latest != null &&
                Objects.equals(query.getX(), latest.getX()) &&
                Objects.equals(query.getY(), latest.getY()) &&
                Objects.equals(query.getR(), latest.getR()) &&
                Objects.equals(query.getResult(), latest.getResult())
        ) {
            return;
        }

        queries.addFirst(query);
    }

    public Deque<Query> getQueries() {
        return queries;
    }

    public Collection<Query> getReversedQueries() {
        return new AbstractCollection<Query>() {

            @Override
            public int size() {
                return queries.size();
            }

            @Override
            public Iterator<Query> iterator() {
                return queries.descendingIterator();
            }
        };
    }
}
