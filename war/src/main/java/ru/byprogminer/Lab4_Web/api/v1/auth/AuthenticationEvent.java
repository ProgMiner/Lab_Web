package ru.byprogminer.Lab4_Web.api.v1.auth;

import ru.byprogminer.Lab4_Web.users.UserEntity;

public class AuthenticationEvent {

    public final UserEntity user;
    public final String token;

    AuthenticationEvent(UserEntity user, String token) {
        this.user = user;
        this.token = token;
    }
}
