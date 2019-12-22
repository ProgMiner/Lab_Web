package ru.byprogminer.Lab4_Web.sessions;

import ru.byprogminer.Lab4_Web.users.UserEntity;

public class SessionEntity {

    private final UserEntity user;
    private final String token;

    public SessionEntity(UserEntity user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
