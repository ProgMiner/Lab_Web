package ru.byprogminer.Lab4_Web.history;

import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Deque;

@Remote
public interface HistoryService extends Serializable {

    /**
     * Returns all queries form user's history
     *
     * @param user user's id
     *
     * @return deque with all queries of user with specified userId, never null
     */
    Deque<QueryEntity> getQueries(UserEntity user);

    /**
     * Returns several queries form user's history
     *
     * @param user user
     * @param offset number of first query
     * @param count count of queries
     *
     * @return deque with queries of user with specified userId, never null
     */
    Deque<QueryEntity> getQueries(UserEntity user, long offset, long count);

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
     * @param user user
     */
    boolean clear(UserEntity user);
}
