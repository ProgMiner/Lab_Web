package ru.byprogminer.Lab4_Web.history;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "history")
@NamedQuery(name = "history.findByUser", query = "from history where user = :user order by id asc")
@NamedQuery(name = "history.deleteByUser", query = "delete from history where user = :user")
public class QueryEntity implements Serializable {

    @Id @GeneratedValue
    private final Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private final UserEntity user;

    @Column(nullable = false)
    private final BigDecimal x, y, r;

    private final boolean result;

    public QueryEntity() {
        id = null;
        user = null;
        x = y = r = null;
        result = false;
    }

    public QueryEntity(Long id, UserEntity user, BigDecimal x, BigDecimal y, BigDecimal r, boolean result) {
        this.id = id;
        this.user = user;
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getR() {
        return r;
    }

    public boolean getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final QueryEntity that = (QueryEntity) o;
        return result == that.result &&
                Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y) &&
                Objects.equals(r, that.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, x, y, r, result);
    }
}
