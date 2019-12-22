package ru.byprogminer.Lab4_Web.impl;

import ru.byprogminer.Lab4_Web.sessions.SessionsService;
import ru.byprogminer.Lab4_Web.users.UsersService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

@Stateless
public class SessionsServiceImpl implements SessionsService {

    @EJB
    private UsersService usersService;

    // TODO replace by repository
    private final Map<String, Set<String>> tokens = new HashMap<>();

    @Override
    public String createSession(String username, String password) {
        if (usersService == null || !usersService.checkPassword(usersService.findUser(username), password)) {
            return null;
        }

        while (true) {
            final String token = UUID.randomUUID().toString();

            if (tokens.computeIfAbsent(username, s -> new HashSet<>()).add(token)) {
                return token;
            }
        }
    }

    @Override
    public boolean destroySession(String username, String token) {
        final Set<String> tokens = this.tokens.get(username);

        return tokens != null && tokens.remove(token);
    }

    @Override
    public boolean checkSession(String username, String token) {
        final Set<String> tokens = this.tokens.get(username);

        return tokens != null && tokens.contains(token);
    }
}
