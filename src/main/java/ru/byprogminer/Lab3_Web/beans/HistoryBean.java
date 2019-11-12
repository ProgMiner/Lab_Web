package ru.byprogminer.Lab3_Web.beans;

import ru.byprogminer.Lab3_Web.Query;
import ru.byprogminer.Lab3_Web.entities.QueryEntity;
import ru.byprogminer.Lab3_Web.services.HistoryService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class HistoryBean {

    private HistoryService historyService;

    public HistoryBean() {}

    void updateHistory(Query query) {
        if (query.getResult() == null) {
            return;
        }

        final Query latest = getQueries().peekLast();
        if (latest != null &&
                Objects.equals(query.getX(), latest.getX()) &&
                Objects.equals(query.getY(), latest.getY()) &&
                Objects.equals(query.getR(), latest.getR()) &&
                Objects.equals(query.getResult(), latest.getResult())
        ) {
            return;
        }

        historyService.addQuery(new QueryEntity(
                query.getX().toPlainString(),
                query.getY().toPlainString(),
                query.getR().toPlainString(),
                query.getResult()
        ));
    }

    public LinkedList<Query> getQueries() {
        return historyService.getHistory().parallelStream().map(e -> new Query(
                new BigDecimal(e.getX()),
                new BigDecimal(e.getY()),
                new BigDecimal(e.getR()),
                e.isResult()
        )).collect(Collectors.toCollection(LinkedList::new));
    }

    public Collection<Query> getReversedQueries() {
        final LinkedList<Query> queries = getQueries();

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

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }
}
