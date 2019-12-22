package ru.byprogminer.Lab4_Web.users;

import javax.ejb.Remote;
import java.io.Serializable;

@Remote
public interface UsersService extends Serializable {

    UserEntity getUser(long userId);
    UserEntity findUser(String username);

    UserEntity createUser(String username, String password);
    boolean removeUser(UserEntity user);

    boolean checkPassword(UserEntity user, String password);

    // TODO
}
