package ru.byprogminer.Lab4_Web.history;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Deque;

@Remote
public interface HistoryService extends Serializable {

    /**
     * Returns all queries form user's history
     *
     * @param userId user's id
     *
     * @return deque with all queries of user with specified userId, never null
     */
    Deque<QueryEntity> getQueries(long userId);

    /**
     * Returns several queries form user's history
     *
     * @param userId user's id
     * @param offset number of first query
     * @param count count of queries
     *
     * @return deque with queries of user with specified userId, never null
     */
    Deque<QueryEntity> getQueries(long userId, long offset, long count);

    /**
     * Adds query to user's history
     *
     * @param query query, never null
     *
     * @return true if query added, false otherwise
     */
    boolean addQuery(@NotNull QueryEntity query);

    /**
     * Clears user's history
     *
     * @param userId user's id
     */
    void clear(long userId);
}
