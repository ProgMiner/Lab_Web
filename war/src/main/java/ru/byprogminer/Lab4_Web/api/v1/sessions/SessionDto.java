package ru.byprogminer.Lab4_Web.api.v1.sessions;

public class SessionDto {

    public final long userId;
    public final String token;

    public SessionDto(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
}
