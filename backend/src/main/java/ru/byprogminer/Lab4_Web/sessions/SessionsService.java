package ru.byprogminer.Lab4_Web.sessions;

import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.ejb.Remote;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Remote
public interface SessionsService extends Serializable {

    /**
     * Creates new user's session
     *
     * @param user user, never null
     *
     * @return token of created session or null if creating failed
     */
    String createSession(@NotNull UserEntity user);

    /**
     * Destroys user's session
     *
     * @param user user, never null
     * @param token token of session for destroying, never null
     *
     * @return true if session destroyed, false otherwise
     */
    boolean destroySession(@NotNull UserEntity user, @NotNull String token);

    /**
     * Checks is user's session exists
     *
     * @param user user, never null
     * @param token token, never null
     *
     * @return true if session of specified user with specified token exists, false otherwise
     */
    boolean checkSession(@NotNull UserEntity user, @NotNull String token);
}
