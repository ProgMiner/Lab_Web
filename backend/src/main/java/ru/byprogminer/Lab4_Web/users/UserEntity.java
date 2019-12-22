package ru.byprogminer.Lab4_Web.users;

import java.io.Serializable;

public class UserEntity implements Serializable {

    private final Long id;

    private final String username;

    private final String passwordHash;
    private final String passwordSalt;

    public UserEntity(Long id, String username, String passwordHash, String passwordSalt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }
}
