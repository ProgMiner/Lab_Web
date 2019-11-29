package ru.byprogminer.Lab3_Web.beans;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import ru.byprogminer.Lab3_Web.Query;
import ru.byprogminer.Lab3_Web.entities.QueryEntity;
import ru.byprogminer.Lab3_Web.services.HistoryService;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HistoryBean extends LazyDataModel<Query> {

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

        historyService.addQuery(new QueryEntity(query.getX(), query.getY(), query.getR(), query.getResult()));
    }

    public LinkedList<Query> getQueries() {
        return historyService.getHistory().parallelStream().map(HistoryBean::entityToQuery)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<Query> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return historyService.getHistoryDesc(first, pageSize).parallelStream()
                .map(HistoryBean::entityToQuery).collect(Collectors.toList());
    }

    @Override
    public List<Query> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
        return load(first, pageSize, null, null, filters);
    }

    @Override
    public int getRowCount() {
        return (int) historyService.getHistoryLength();
    }

    private static Query entityToQuery(QueryEntity entity) {
        return new Query(entity.getX(), entity.getY(), entity.getR(), entity.isResult());
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }
}
