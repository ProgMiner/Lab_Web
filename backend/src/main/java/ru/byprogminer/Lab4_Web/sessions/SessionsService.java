package ru.byprogminer.Lab4_Web.sessions;

import javax.ejb.Remote;
import java.io.Serializable;

@Remote
public interface SessionsService extends Serializable {

    String createSession(String username, String password);
    boolean destroySession(String username, String token);

    boolean checkSession(String username, String token);
}
