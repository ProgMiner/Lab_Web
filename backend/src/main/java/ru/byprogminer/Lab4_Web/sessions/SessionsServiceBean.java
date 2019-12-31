package ru.byprogminer.Lab4_Web.sessions;

import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class SessionsServiceBean implements SessionsService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> getSessionTokens(@NotNull UserEntity user) {
        return entityManager.createNamedQuery("sessions.findByUser", SessionEntity.class)
                .setParameter("user", user).getResultStream()
                .map(SessionEntity::getToken).collect(Collectors.toList());
    }

    @Override
    public String createSession(@NotNull UserEntity user) throws PersistenceException {
        final String token = UUID.randomUUID().toString();

        // I hope that UUID.randomUUID() never made token with collision
        entityManager.persist(new SessionEntity(entityManager.getReference(UserEntity.class, user.getId()), token));
        return token;
    }

    @Override
    public boolean destroySession(@NotNull UserEntity user, @NotNull String token) {
        final SessionEntity session = entityManager.find(SessionEntity.class, new SessionEntity.PrimaryKey(user, token));

        if (session == null) {
            return false;
        }

        entityManager.remove(session);
        return true;
    }

    @Override
    public boolean checkSession(@NotNull UserEntity user, @NotNull String token) {
        return entityManager.find(SessionEntity.class, new SessionEntity.PrimaryKey(user, token)) != null;
    }
}
