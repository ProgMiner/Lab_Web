package ru.byprogminer.Lab4_Web.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "users")
@NamedQuery(name = "users.findByUsername", query = "from users where username = :username")
public class UserEntity implements Serializable {

    @Id @GeneratedValue
    private final Long id;

    @Column(nullable = false, unique = true)
    private final String username;

    @Column(nullable = false)
    private final String passwordHash;

    @Column(nullable = false)
    private final String passwordSalt;

    public UserEntity() {
        id = null;
        username = null;
        passwordHash = null;
        passwordSalt = null;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(passwordHash, that.passwordHash) &&
                Objects.equals(passwordSalt, that.passwordSalt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, passwordHash, passwordSalt);
    }
}
