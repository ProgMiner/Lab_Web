package ru.byprogminer.Lab4_Web.history;

import javax.ejb.Remote;
import java.io.Serializable;
import java.util.Deque;

@Remote
public interface HistoryService extends Serializable {

    Deque<QueryEntity> getQueries(long userId);

    boolean addQuery(QueryEntity query);
    void clear(long userId);
}
