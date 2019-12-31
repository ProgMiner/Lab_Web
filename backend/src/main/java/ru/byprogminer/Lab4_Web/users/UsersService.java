package ru.byprogminer.Lab4_Web.users;

import javax.ejb.Remote;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Remote
public interface UsersService extends Serializable {

    /**
     * Returns user by userId or null if it isn't exists
     *
     * @param userId user id
     *
     * @return user with specified id or null
     */
    UserEntity getUser(long userId);

    /**
     * Returns user by username or null if it isn't exists
     *
     * @param username username, never null
     *
     * @return user with specified username or null
     */
    UserEntity findUser(@NotNull String username);

    /**
     * Creates new user
     *
     * @param username username, never null
     * @param password password, never null
     *
     * @return created user or null if creating failed
     */
    UserEntity createUser(@NotNull @Size(min = 2) @NotBlank String username, @NotNull @NotBlank String password);

    /**
     * Removes specified user
     *
     * @param user user, never null
     *
     * @return true if user removed, false otherwise
     */
    boolean removeUser(@NotNull UserEntity user);

    /**
     * Checks is password correct
     *
     * @param user user, never null
     * @param password password, never null
     *
     * @return true if specified password matches to real user's password
     */
    boolean checkPassword(@NotNull UserEntity user, @NotNull String password);
}
