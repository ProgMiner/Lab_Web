package ru.byprogminer.Lab4_Web.impl;

import ru.byprogminer.Lab4_Web.sessions.SessionsService;
import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.util.*;

// @Stateless
@Singleton
public class SessionsServiceImpl implements SessionsService {

    // TODO replace by repository
    private final Map<Long, Set<String>> tokens = new WeakHashMap<>();

    @Override
    public String createSession(@NotNull UserEntity user) {
        while (true) {
            final String token = UUID.randomUUID().toString();

            if (tokens.computeIfAbsent(user.getId(), u -> Collections.newSetFromMap(new WeakHashMap<>())).add(token)) {
                return token;
            }
        }
    }

    @Override
    public boolean destroySession(@NotNull UserEntity user, @NotNull String token) {
        final Set<String> tokens = this.tokens.get(user.getId());

        return tokens != null && tokens.remove(token);
    }

    @Override
    public boolean checkSession(@NotNull UserEntity user, @NotNull String token) {
        final Set<String> tokens = this.tokens.get(user.getId());

        return tokens != null && tokens.contains(token);
    }
}
