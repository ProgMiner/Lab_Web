package ru.byprogminer.Lab4_Web.sessions;

import ru.byprogminer.Lab4_Web.users.UserEntity;

import java.util.Objects;

public class SessionEntity {

    private final Long id;

    private final UserEntity user;
    private final String token;

    public SessionEntity(Long id, UserEntity user, String token) {
        this.id = id;
        this.user = user;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SessionEntity that = (SessionEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, token);
    }
}
